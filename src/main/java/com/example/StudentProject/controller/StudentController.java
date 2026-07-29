package com.example.StudentProject.controller;

import com.example.StudentProject.dto.StudentCreateRequestDto;
import com.example.StudentProject.dto.StudentCreateResponseDto;
import com.example.StudentProject.dto.StudentUpdateRequestDto;
import com.example.StudentProject.dto.StudentUpdateResponseDto;
import com.example.StudentProject.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/students")
public class StudentController {

    private StudentService studentService;

    public StudentController(StudentService studentService){
        this.studentService = studentService;
    }
    @PostMapping
    public ResponseEntity<StudentCreateResponseDto> createStudent(@Valid @RequestBody StudentCreateRequestDto requestDto){
        StudentCreateResponseDto responseDto = studentService.createStudent(requestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentCreateResponseDto> getStudent(@PathVariable Long id){
        StudentCreateResponseDto studentResp = studentService.getStudent(id);
        return ResponseEntity.status(HttpStatus.OK).body(studentResp);
    }

    @GetMapping
    public ResponseEntity<List<StudentCreateResponseDto>> getAllStudent(){
        List<StudentCreateResponseDto> StudentList = studentService.getAllStudent();
        return ResponseEntity.status(HttpStatus.OK).body(StudentList);
    }

    @PutMapping
    public ResponseEntity<StudentUpdateResponseDto> updateStudent(@RequestParam Long id,
                                                                  @RequestBody StudentUpdateRequestDto student){

        StudentUpdateResponseDto studentResp = studentService.update(id,student);
        return ResponseEntity.status(HttpStatus.OK).body(studentResp);
    }
    @DeleteMapping
    public ResponseEntity<Boolean> deleteStudent(@RequestParam Long id){
        studentService.deleteStudent(id);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PatchMapping("/delete-soft")
    public ResponseEntity<Boolean> deleteStudentSoftly(@RequestParam Long  id){
        studentService.deleteStudentSoftly(id);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
