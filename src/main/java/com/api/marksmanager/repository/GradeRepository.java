package com.api.marksmanager.repository;

import com.api.marksmanager.entity.Grade;
import org.springframework.data.repository.CrudRepository;

public interface GradeRepository extends CrudRepository<Grade, Long> {
}
