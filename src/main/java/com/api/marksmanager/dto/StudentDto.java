package com.api.marksmanager.dto;

import com.api.marksmanager.entity.Course;
import com.api.marksmanager.entity.Grade;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
public class StudentDto {

    private Long id;
    private String firstName;
    private String lastName;
    private int age;
    private String studentMail;
    private Set<Grade> grades;
    private Set<Course> courses;

    public StudentDto() {}

    public StudentDto(String firstName, String lastName, int age, String studentMail, Set<Grade> grades, Set<Course> courses) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.studentMail = studentMail;
        this.grades = grades;
        this.courses = courses;
    }
}
