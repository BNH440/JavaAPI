package com.blakehaug.java_api.student;

import com.blakehaug.java_api.user.RoleRepository;
import com.blakehaug.java_api.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class StudentController { // sets up student endpoints
    @Autowired
    private StudentRepository studentRepo;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    UserService users;

    @PreAuthorize("hasAuthority('ADMIN')") // restricts listing all students to only users with the ADMIN role
    @GetMapping("/students")
    public ResponseEntity<List<Student>> listAll() {
        List<Student> students = studentRepo.findAll();

        if (students.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(students);
        }
    }

    @GetMapping("/students/get/{id}") // gets a student by id
    public ResponseEntity<Student> listOne(@PathVariable Integer id, HttpServletRequest request, Principal principal){
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        boolean admin = authorities.contains(new SimpleGrantedAuthority("ADMIN"));

        if(!(admin || users.findByUsername(principal.getName()).get().getStudent().getId().equals(id))) {
            return ResponseEntity.status(403).build();
        } // restricts listing one student to only users with the ADMIN role or the user with the given student id

        Optional<Student> student = studentRepo.findById(id);

        if(student.isPresent()) {
            return ResponseEntity.ok(student.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PreAuthorize("hasAuthority('ADMIN')") // restricts creating a student to only users with the ADMIN role TODO change later to add creation to the signup process
    @PostMapping("/students/create") // creates a student
    public ResponseEntity<Student> create(@RequestBody Student student) {
        Student newStudent = studentRepo.save(student);
        return ResponseEntity.ok(newStudent);
    }
}
