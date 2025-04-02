package com.api.marksmanager.repository;

import com.api.marksmanager.entity.Student;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface StudentRepository extends CrudRepository<Student, Long> {

    Optional<Student> findByFirstname(String firstname);
    Optional<Student> findByLastname(String lastname);

    Student findById(long id);

    boolean existsByStudentMail(String email);
}
