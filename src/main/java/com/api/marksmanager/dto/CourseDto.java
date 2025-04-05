package com.api.marksmanager.dto;

import com.api.marksmanager.entity.Student;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
public class CourseDto {

    private Long id;
    private String name;
    private String description;
    private int durationInHours;
    private Set<Student> students;

    public CourseDto() {}

    public CourseDto(String name, String description, int durationInHours, Set<Student> students) {
        this.name = name;
        this.description = description;
        this.durationInHours = durationInHours;
        this.students = students;
    }
}
