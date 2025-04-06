package com.api.marksmanager.controller;

import com.api.marksmanager.entity.Course;
import com.api.marksmanager.entity.Grade;
import com.api.marksmanager.entity.Student;
import com.api.marksmanager.repository.CourseRepository;
import com.api.marksmanager.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/reports")
public class ReportController {

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    StudentRepository studentRepository;

    @GetMapping("/course/{courseId}")
    public ResponseEntity<?> generateCourseReport(@PathVariable Long courseId) {
        Optional<Course> course = courseRepository.findById(courseId);
        if (course.isPresent()) {
            Set<Grade> grades = course.get().getGrades();
            float averageGrade = 0.0f;
            for (Grade grade : grades) {
                averageGrade += grade.getGrade();
            }
            averageGrade /= grades.size();
            return ResponseEntity.status(HttpStatus.OK).body(averageGrade);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<?> generateStudentReport(@PathVariable Long studentId) {
        Optional<Student> student = studentRepository.findById(studentId);
        if (student.isPresent()) {
            Set<Grade> grades = student.get().getGrades();
            float averageGrade = 0.0f;
            for (Grade grade : grades) {
                averageGrade += grade.getGrade();
            }
            averageGrade /= grades.size();
            return ResponseEntity.status(HttpStatus.OK).body(averageGrade);
        }
        return ResponseEntity.notFound().build();
    }
}
