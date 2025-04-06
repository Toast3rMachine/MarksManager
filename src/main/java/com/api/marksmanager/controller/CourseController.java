package com.api.marksmanager.controller;

import com.api.marksmanager.dto.CourseDto;
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
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private StudentRepository studentRepository;

    @PostMapping
    public ResponseEntity<?> registerCourse(@Valid @RequestBody CourseDto courseDto) {

        if (courseRepository.existsByName(courseDto.getName())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Ce cours existe déjà"));
        }

        Course course = new Course();
        course.setName(courseDto.getName());
        course.setDescription(courseDto.getDescription());
        course.setDurationInHours(courseDto.getDurationInHours());

        if (!courseDto.getStudents().isEmpty()) {
            Set<Student> students = new HashSet<>();
            for (Student student : courseDto.getStudents()) {
                Student newStudent = new Student();

                if (student.getId() != null) {
                    newStudent = studentRepository.findById(student.getId()).get();
                } else if (studentRepository.existsByStudentMail(student.getStudentMail())) {
                    newStudent = studentRepository.findByFirstName(student.getFirstName()).get();
                } else {
                    newStudent.setFirstName(student.getFirstName());
                    newStudent.setLastName(student.getLastName());
                    newStudent.setAge(student.getAge());
                    newStudent.setStudentMail(student.getStudentMail());
                    Set<Course> courses = new HashSet<>();
                    courses.add(course);
                    newStudent.setCourses(courses);
                    studentRepository.save(newStudent);
                }
                students.add(newStudent);
            }
            course.setStudents(students);
        }

        courseRepository.save(course);

        return ResponseEntity.ok().body(new MessageResponse("Nouveau cours enregistré avec succès"));
    }

    @GetMapping
    public ResponseEntity<?> getAllCourses() {
        return ResponseEntity.ok().body(courseRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCourseById(@PathVariable long id) {
        if (courseRepository.existsById(id)) {
            Course course = courseRepository.findById(id);
            return ResponseEntity.ok().body(course);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCourse(@PathVariable Long id, @Valid @RequestBody CourseDto courseDto) {
        Course course = courseRepository.findById(id).orElse(null);
        if (course != null) {
            course.setName(courseDto.getName());
            course.setDescription(courseDto.getDescription());
            course.setDurationInHours(courseDto.getDurationInHours());

            if (!courseDto.getStudents().isEmpty()) {
                System.out.println("Entré dans la condition");
                Set<Student> students = new HashSet<>();
                for (Student student : courseDto.getStudents()) {
                    Student newStudent = studentRepository.findById(student.getId()).get();
                    System.out.println(student.getId());

                    if (student.getId() != null) {
                        System.out.println(1);
                        newStudent = studentRepository.findById(student.getId()).get();
                    } else if (studentRepository.existsByStudentMail(student.getStudentMail())) {
                        System.out.println(2);
                        newStudent = studentRepository.findByFirstName(student.getFirstName()).get();
                    } else {
                        System.out.println(3);
                        newStudent.setFirstName(student.getFirstName());
                        newStudent.setLastName(student.getLastName());
                        newStudent.setAge(student.getAge());
                        newStudent.setStudentMail(student.getStudentMail());
                    }
                    Set<Course> courses = new HashSet<>(newStudent.getCourses());
                    courses.add(course);
                    newStudent.setCourses(courses);
                    studentRepository.save(newStudent);
                    students.add(newStudent);
                }
                course.setStudents(students);
            }
            courseRepository.save(course);
            return ResponseEntity.ok().body(new MessageResponse("Cours mis à jour avec succès"));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable long id) {
        if (courseRepository.existsById(id)) {
            Course deletedCourse = courseRepository.findById(id);
            for (Student student : deletedCourse.getStudents()) {
                student.removeCourse(deletedCourse);
            }
            courseRepository.deleteById(id);
            return ResponseEntity.ok().body(new MessageResponse("Cours supprimé avec succès"));
        }
        return ResponseEntity.notFound().build();
    }
}
