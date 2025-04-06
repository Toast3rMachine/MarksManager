package com.api.marksmanager.repository;

import com.api.marksmanager.entity.Course;
import org.springframework.data.repository.CrudRepository;

public interface CourseRepository extends CrudRepository<Course, Long> {

    Course findById(long id);
    Course findByName(String name);

    Boolean existsById(long id);
    Boolean existsByName(String name);
}
