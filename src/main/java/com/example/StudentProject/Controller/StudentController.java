package com.example.StudentProject.Controller;

import com.example.StudentProject.DTO.StudentCreateRequestDto;
import com.example.StudentProject.DTO.StudentCreateResponseDto;
import com.example.StudentProject.DTO.StudentUpdateRequestDto;
import com.example.StudentProject.DTO.StudentUpdateResponseDto;
import com.example.StudentProject.Entity.Student;
import com.example.StudentProject.Service.StudentService;
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

        if(studentResp==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(studentResp);
    }

    @GetMapping
    public ResponseEntity<List<StudentCreateResponseDto>> getAllStudent(){
        List<StudentCreateResponseDto> StudentList = studentService.getAllStudent();

        if(StudentList==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(StudentList);
    }

    @PutMapping("/update")
    public ResponseEntity<StudentUpdateResponseDto> updateStudent(@RequestParam Long id,
                                                                  @RequestBody StudentUpdateRequestDto student){

        StudentUpdateResponseDto studentResp = studentService.update(id,student);

        if(studentResp==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(studentResp);
    }
    @DeleteMapping("delete")
    public ResponseEntity<Boolean> deleteStudent(@RequestParam Long id){
        boolean isDeleted = studentService.deleteStudent(id);

        if(!isDeleted){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(true);
    }

    @PatchMapping("/delete-soft")
    public ResponseEntity<Boolean> deleteStudentSoftly(@RequestParam Long  id){
        boolean isDeleted = studentService.deleteStudentSoftly(id);
        if(!isDeleted){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(true);

    }
}
