package com.example.StudentProject.Service;

import com.example.StudentProject.DTO.StudentCreateRequestDto;
import com.example.StudentProject.DTO.StudentCreateResponseDto;
import com.example.StudentProject.DTO.StudentUpdateRequestDto;
import com.example.StudentProject.DTO.StudentUpdateResponseDto;
import com.example.StudentProject.Entity.Student;
import com.example.StudentProject.Repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }

    public StudentCreateResponseDto createStudent(StudentCreateRequestDto reqDto){
        Student student = mapCreateStudentRequestDtoToEntity(reqDto);
        Student studentResponse = studentRepository.save(student);

        StudentCreateResponseDto responseDto = mapToCreateStudentResponseDto(studentResponse);
        return responseDto;
    }



    public StudentCreateResponseDto getStudent(Long id){
        Optional<Student> StudentResp = studentRepository.findByIdAndDeletedIsFalse(id);

        if(StudentResp.isPresent()){
            Student studentResult = StudentResp.get();

            StudentCreateResponseDto responseDto = mapToCreateStudentResponseDto(studentResult);
            return responseDto;
        }
        return null;
    }

    public List<StudentCreateResponseDto> getAllStudent(){
        List<Student> studentList = studentRepository.findByDeletedIsFalse();

        return studentList.stream()
                .map(this::mapToCreateStudentResponseDto)
                .toList();
    }

    public StudentUpdateResponseDto update(Long id, StudentUpdateRequestDto requestDto){
        // if exists check
        Optional<Student> existingRecord = studentRepository.findByIdAndDeletedIsFalse(id);

        if(existingRecord.isEmpty()){
            return null;
        }
        Student existingStudent = existingRecord.get();
        existingStudent.setRollNo(requestDto.getRollNo());
        existingStudent.setAge(requestDto.getAge());
        existingStudent.setCourse(requestDto.getCourse());
        existingStudent.setUpdatedAt(LocalDateTime.now());

        studentRepository.save(existingStudent);

        StudentUpdateResponseDto responseDto = mapToUpdateStudentDto(existingStudent);
        return responseDto;
    }


    public boolean deleteStudent(Long id){
        boolean exists = studentRepository.existsById(id);

        if(!exists){
            return false;
        }
        studentRepository.deleteById(id);
        return true;
    }

    public boolean deleteStudentSoftly(Long id){
        Optional<Student> existingRecord = studentRepository.findByIdAndDeletedIsFalse(id);

        if(existingRecord.isEmpty()){
            return false;
        }
        Student existingStudent = existingRecord.get();
        existingStudent.setDeleted(true);
        studentRepository.save(existingStudent);
        return true;

    }
    private StudentCreateResponseDto mapToCreateStudentResponseDto(Student student) {
        StudentCreateResponseDto responseDto = new StudentCreateResponseDto();

        responseDto.setId(student.getId());
        responseDto.setName(student.getName());
        responseDto.setAge(student.getAge());
        responseDto.setEmail(student.getEmail());
        responseDto.setRollNo(student.getRollNo());
        responseDto.setCourse(student.getCourse());
        responseDto.setCreatedAt(student.getCreatedAt());
        responseDto.setUpdatedAt(student.getUpdatedAt());

        return responseDto;
    }

    private Student mapCreateStudentRequestDtoToEntity(StudentCreateRequestDto reqDto) {
        Student student = new Student();
        student.setDeleted(false);

        student.setName(reqDto.getName());
        student.setEmail(reqDto.getEmail());
        student.setAge(reqDto.getAge());
        student.setCourse(reqDto.getCourse());
        student.setRollNo(reqDto.getRollNo());
        student.setCreatedAt(LocalDateTime.now());
        student.setUpdatedAt(LocalDateTime.now());

        return student;
    }


    private StudentUpdateResponseDto mapToUpdateStudentDto(Student student) {
        StudentUpdateResponseDto responseDto = new StudentUpdateResponseDto();
        responseDto.setId(student.getId());
        responseDto.setName(student.getName());
        responseDto.setAge(student.getAge());
        responseDto.setEmail(student.getEmail());
        responseDto.setRollNo(student.getRollNo());
        responseDto.setCourse(student.getCourse());
        responseDto.setCreatedAt(student.getCreatedAt());
        responseDto.setUpdatedAt(student.getUpdatedAt());

        return responseDto;
    }
}
