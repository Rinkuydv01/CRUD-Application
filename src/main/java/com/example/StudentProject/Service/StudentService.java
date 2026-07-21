package com.example.StudentProject.Service;

import com.example.StudentProject.Entity.Student;
import com.example.StudentProject.Repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student studentReq){
        Student studentResp = studentRepository.save(studentReq);

        return studentResp;
    }

    public Student getStudent(Long id){
        Optional<Student> StudentResp = studentRepository.findById(id);

        if(StudentResp.isPresent()){
            return StudentResp.get();
        }
        return null;
    }

    public List<Student> getAllStudent(){
        List<Student> studentList = studentRepository.findAll();

        return studentList;
    }

    public Student update(Long id,Student student){
        // if exists check
        Optional<Student> existingRecord = studentRepository.findById(id);

        if(existingRecord.isEmpty()){
            return null;
        }
        Student existingStudent = existingRecord.get();
        existingStudent.setName(student.getName());
        existingStudent.setRollNo(student.getRollNo());
        existingStudent.setAge(student.getAge());
        existingStudent.setCourse(student.getCourse());
        existingStudent.setEmail(student.getEmail());

        studentRepository.save(existingStudent);

        return existingStudent;
    }

    public boolean deleteStudent(Long id){
        boolean exists = studentRepository.existsById(id);

        if(!exists){
            return false;
        }
        studentRepository.deleteById(id);
        return true;
    }
}
