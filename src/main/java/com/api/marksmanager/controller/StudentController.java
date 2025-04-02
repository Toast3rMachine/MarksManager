package com.api.marksmanager.controller;

import com.api.marksmanager.dto.StudentDto;
import com.api.marksmanager.entity.Student;
import com.api.marksmanager.payload.request.RegisterRequest;
import com.api.marksmanager.payload.response.MessageResponse;
import com.api.marksmanager.repository.StudentRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    StudentRepository studentRepository;

    @PostMapping
    public ResponseEntity<?> registerStudent(@Valid @RequestBody RegisterRequest registerRequest) {

        if (studentRepository.existsByStudentMail(registerRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Email déjà attribué"));
        }

        Student student = new Student(registerRequest.getFirstName(), registerRequest.getLastName(), registerRequest.getAge(), registerRequest.getEmail());

        studentRepository.save(student);

        return ResponseEntity.ok(new MessageResponse("Nouvel étudiant enregistré avec succès"));
    }

    @GetMapping
    public ResponseEntity<?> getAllStudents() {
        return ResponseEntity.ok(studentRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable long id) {
        if (studentRepository.existsById(id)) {
            Student student = studentRepository.findById(id);
            return ResponseEntity.ok().body(student);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateStudent(@PathVariable Long id, @Valid @RequestBody StudentDto studentDto) {
        Student student = studentRepository.findById(id).orElse(null);
        if (student != null) {
            student.setFirstname(studentDto.getFirstName());
            student.setLastname(studentDto.getLastName());
            student.setAge(studentDto.getAge());
            if (studentDto.getMarks() != null) {
                student.setMarks(studentDto.getMarks());
            }
            studentRepository.save(student);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable long id) {
        if (studentRepository.existsById(id)) {
            studentRepository.deleteById(id);
            return ResponseEntity.ok().body(new MessageResponse("Etudiant supprimé avec succés"));
        }
        return ResponseEntity.notFound().build();
    }
}
