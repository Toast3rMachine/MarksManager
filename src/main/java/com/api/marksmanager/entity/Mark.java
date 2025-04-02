package com.api.marksmanager.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Mark")
public class Mark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "value", nullable = false)
    private int value;

    @ManyToOne
    private Student student;

    public Mark() {}

    public Mark(Student student, int value) {
        this.student = student;
        this.value = value;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
