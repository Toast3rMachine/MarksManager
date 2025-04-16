package com.api.marksmanager.controller;

import com.api.marksmanager.dto.StudentDto;
import com.api.marksmanager.entity.Course;
import com.api.marksmanager.entity.Student;
import com.api.marksmanager.payload.response.MessageResponse;
import com.api.marksmanager.repository.CourseRepository;
import com.api.marksmanager.repository.StudentRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    StudentRepository studentRepository;
    @Autowired
    private CourseRepository courseRepository;

    @PostMapping
    public ResponseEntity<?> registerStudent(@Valid @RequestBody StudentDto studentDto) {

        if (studentRepository.existsByStudentMail(studentDto.getStudentMail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Email déjà attribué"));
        }

        Student student = new Student();
        if (updateStudent(studentDto, student)){
            return ResponseEntity.ok(new MessageResponse("Nouvel.le étudiant.e enregistré.e avec succès"));
        }
        return ResponseEntity.badRequest().body(new MessageResponse("Veuillez renseigner un cours existant"));
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
             if (updateStudent(studentDto, student)){
                 return ResponseEntity.ok().body(new MessageResponse("Etudiant mis à jour avec succès"));
             }
            return ResponseEntity.badRequest().body(new MessageResponse("Veuillez renseigner un cours existant"));
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

    private boolean updateStudent(@RequestBody @Valid StudentDto studentDto, Student student) {
        student.setFirstName(studentDto.getFirstName());
        student.setLastName(studentDto.getLastName());
        student.setAge(studentDto.getAge());
        student.setStudentMail(studentDto.getStudentMail());
        if (!studentDto.getCourses().isEmpty() && courseRepository.count() != 0) {
            Set<Course> courses = new HashSet<>();
            for (Course course : studentDto.getCourses()) {
                Course newCourse;

                if (course.getId() != null) { //* Get a Course by ID if passed in the request
                    newCourse = courseRepository.findById(course.getId()).get();

                } else if (courseRepository.existsByName(course.getName())) { //* Get a Course if it already exist. Prevent Courses duplication.
                    newCourse = courseRepository.findByName(course.getName());

                } else { //* If a course not exist, return a bad request
                    return false;
                }

                courses.add(newCourse);
            }
            student.setCourses(courses);
        }

        studentRepository.save(student);
        return true;
    }
}
