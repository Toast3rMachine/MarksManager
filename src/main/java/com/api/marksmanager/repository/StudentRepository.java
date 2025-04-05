package com.api.marksmanager.repository;

import com.api.marksmanager.entity.Student;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.Set;

public interface StudentRepository extends CrudRepository<Student, Long> {

    Optional<Student> findByFirstName(String firstName);
    Optional<Student> findByLastName(String lastName);

    Student findByIdIn(Set<Long> ids);
    Student findById(@NotBlank long id);

    boolean existsByStudentMail(String email);
}
