package com.blakehaug.java_api;

import com.blakehaug.java_api.user.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Role;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RestController
public class StudentController {
    @Autowired
    private StudentRepository studentRepo;

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("/students")
    public ResponseEntity<List<Student>> listAll() {
        List<Student> students = studentRepo.findAll();

        if (students.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(students);
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/students/get/{id}")
    public ResponseEntity<Student> listOne(@PathVariable Integer id, HttpServletRequest request, Principal principal){
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        boolean authorized = authorities.contains(new SimpleGrantedAuthority("ADMIN"));

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
