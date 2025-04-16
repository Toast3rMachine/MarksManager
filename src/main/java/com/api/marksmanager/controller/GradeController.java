package com.api.marksmanager.controller;

import com.api.marksmanager.dto.GradeDto;
import com.api.marksmanager.entity.Course;
import com.api.marksmanager.entity.Grade;
import com.api.marksmanager.entity.Student;
import com.api.marksmanager.repository.CourseRepository;
import com.api.marksmanager.repository.GradeRepository;
import com.api.marksmanager.repository.StudentRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/grades")
public class GradeController {

    @Autowired
    GradeRepository gradeRepository;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    CourseRepository courseRepository;

    @PostMapping
    public ResponseEntity<?> registerGrade(@Valid @RequestBody GradeDto gradeDto) {

        Grade grade = new Grade();
        updateGrade(gradeDto, grade);

        return ResponseEntity.status(HttpStatus.CREATED).body(gradeRepository.save(grade));
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<?> getStudentGrade(@PathVariable Long studentId) {
        Optional<Student> student = studentRepository.findById(studentId);
        if (student.isPresent()) {
            Set<Grade> grades = student.get().getGrades();

            return ResponseEntity.status(HttpStatus.OK).body(grades);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<?> getCourseGrade(@PathVariable Long courseId) {
        Optional<Course> course = courseRepository.findById(courseId);
        if (course.isPresent()) {
            Set<Grade> grades = course.get().getGrades();
            return ResponseEntity.status(HttpStatus.OK).body(grades);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateGrade(@PathVariable Long id, @Valid @RequestBody GradeDto gradeDto) {
        Grade grade = gradeRepository.findById(id).orElse(null);
        if (grade != null) {
            updateGrade(gradeDto, grade);
            gradeRepository.save(grade);
            return ResponseEntity.status(HttpStatus.OK).body(grade);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGrade(@PathVariable Long id) {
        if (gradeRepository.existsById(id)) {
            gradeRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    private void updateGrade(@RequestBody @Valid GradeDto gradeDto, Grade grade) {
        grade.setGrade(gradeDto.getGrade());

        Optional<Student> student = studentRepository.findById(gradeDto.getStudentId());
        student.ifPresent(grade::setStudent);

        Optional<Course> course = courseRepository.findById(gradeDto.getCourseId());
        course.ifPresent(grade::setCourse);
    }
}
