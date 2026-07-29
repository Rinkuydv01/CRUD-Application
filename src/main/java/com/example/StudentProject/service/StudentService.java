package com.example.StudentProject.service;

import com.example.StudentProject.dto.StudentCreateRequestDto;
import com.example.StudentProject.dto.StudentCreateResponseDto;
import com.example.StudentProject.dto.StudentUpdateRequestDto;
import com.example.StudentProject.dto.StudentUpdateResponseDto;
import com.example.StudentProject.entity.Student;
import com.example.StudentProject.repository.StudentRepository;
import com.example.StudentProject.exception.DuplicateResourceException;
import com.example.StudentProject.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StudentService {
    StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }

    public StudentCreateResponseDto createStudent(StudentCreateRequestDto reqDto){
        Student student = mapCreateStudentRequestDtoToEntity(reqDto);

        if(emailExists(student)){
            throw new DuplicateResourceException("Student with email ID "+ student.getEmail() +" already exists");
        }
        Student studentResponse = studentRepository.save(student);
        StudentCreateResponseDto responseDto = mapToCreateStudentResponseDto(studentResponse);
        return responseDto;
    }

    public StudentCreateResponseDto getStudent(Long id){
        Student studentResponse = studentRepository
                .findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Student with id-" + id + " not found"));

        return mapToCreateStudentResponseDto(studentResponse);
    }

    public List<StudentCreateResponseDto> getAllStudent(){
        List<Student> studentList = studentRepository.findByDeletedIsFalse();

        return studentList.stream()
                .map(this::mapToCreateStudentResponseDto)
                .toList();
    }

    public StudentUpdateResponseDto update(Long id, StudentUpdateRequestDto requestDto){
        Student existingRecord = studentRepository
                .findByIdAndDeletedIsFalse(id)
                .orElseThrow(()->new ResourceNotFoundException("Student with id-" + id + " not found"));



        existingRecord.setRollNo(requestDto.getRollNo());
        existingRecord.setAge(requestDto.getAge());
        existingRecord.setCourse(requestDto.getCourse());
        existingRecord.setUpdatedAt(LocalDateTime.now());

        studentRepository.save(existingRecord);

        StudentUpdateResponseDto responseDto = mapToUpdateStudentDto(existingRecord);
        return responseDto;
    }


    public void deleteStudent(Long id){
        if(!studentRepository.existsById(id)){
            throw new ResourceNotFoundException("Student with id-" + id + " not found");
        }

        studentRepository.deleteById(id);

    }

    public void deleteStudentSoftly(Long id){
        Student existingRecord = studentRepository
                .findByIdAndDeletedIsFalse(id)
                .orElseThrow(()->new ResourceNotFoundException("Student with id-" + id + " not found"));

        existingRecord.setDeleted(true);
        studentRepository.save(existingRecord);

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

    private boolean emailExists(Student student) {
        return studentRepository.existsByEmail(student.getEmail());
    }
}
