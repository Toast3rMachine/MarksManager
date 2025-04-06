package com.api.marksmanager.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GradeDto {

    private Long id;
    private int grade;
    private Long studentId;
    private Long courseId;

    public GradeDto() {}

    public GradeDto(int grade, Long studentId, Long courseId) {
        this.grade = grade;
        this.studentId = studentId;
        this.courseId = courseId;
    }
}
