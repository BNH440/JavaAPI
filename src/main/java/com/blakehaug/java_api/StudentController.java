package com.blakehaug.java_api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class StudentController {
    @Autowired
    private StudentRepository studentRepo;

    @GetMapping("/students")
    public ResponseEntity<List<Student>> listAll() {
        List<Student> students = studentRepo.findAll();

        if (students.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(students);
        }
    }

    @GetMapping("/students/get/{id}")
    public ResponseEntity<Student> listOne(@PathVariable Integer id) {
        Optional<Student> student = studentRepo.findById(id);

        if(student.isPresent()) {
            return ResponseEntity.ok(student.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/students/create")
    public ResponseEntity<Student> create(@RequestBody Student student) {
        Student newStudent = studentRepo.save(student);
        return ResponseEntity.ok(newStudent);
    }
}
