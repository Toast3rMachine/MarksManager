package com.api.marksmanager.controller;

import com.api.marksmanager.dto.CourseDto;
import com.api.marksmanager.entity.Course;
import com.api.marksmanager.payload.request.CreateCourseRequest;
import com.api.marksmanager.payload.response.MessageResponse;
import com.api.marksmanager.repository.CourseRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    CourseRepository courseRepository;

    @PostMapping
    public ResponseEntity<?> createCourse(@Valid @RequestBody CreateCourseRequest createCourseRequest) {

        Course course = new Course(createCourseRequest.getName(), createCourseRequest.getDate(), createCourseRequest.getStartHour(), createCourseRequest.getEndHour());

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
            course.setStartHour(courseDto.getStartHour());
            course.setEndHour(courseDto.getEndHour());
            courseRepository.save(course);
            return ResponseEntity.ok().body(new MessageResponse("Cours mis à jour avec succès"));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable long id) {
        if (courseRepository.existsById(id)) {
            courseRepository.deleteById(id);
            return ResponseEntity.ok().body(new MessageResponse("Cours supprimé avec succès"));
        }
        return ResponseEntity.notFound().build();
    }
}
