package com.example.StudentProject.Controller;

import com.example.StudentProject.Entity.Student;
import com.example.StudentProject.Service.StudentService;
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
    @PostMapping("/create")
    public int createStudent(@RequestBody Student student){
        Student createdStudent = studentService.createStudent(student);

        return createdStudent.getAge();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Student> getStudent(@PathVariable Long id){
        Student studentResp = studentService.getStudent(id);

        if(studentResp==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(studentResp);
    }

    @GetMapping("/get")
    public ResponseEntity<List<Student>> getAllStudent(){
        List<Student> StudentList = studentService.getAllStudent();

        if(StudentList==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(StudentList);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id,
                                                 @RequestBody Student student){

        Student studentResp = studentService.update(id,student);

        if(studentResp==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(studentResp);
    }
    @DeleteMapping("delete/{id}")
    public ResponseEntity<Boolean> deleteStudent(@PathVariable Long id){
        boolean isDeleted = studentService.deleteStudent(id);

        if(!isDeleted){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(true);
    }

    @PatchMapping("/delete-soft/{id}")
    public ResponseEntity<Boolean> deleteStudentSoftly(@PathVariable Long  id){
        boolean isDeleted = studentService.deleteStudentSoftly(id);
        if(!isDeleted){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(true);

    }
}
