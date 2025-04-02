package com.api.marksmanager;

import com.api.marksmanager.entity.Student;
import com.api.marksmanager.repository.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MarksmanagerApplication {

	public static void main(String[] args) {

		SpringApplication.run(MarksmanagerApplication.class, args);

	}

	@Bean
	public CommandLineRunner test(StudentRepository studentRepository) {
		return (args) -> {
			studentRepository.save(new Student("John", "Doe", 17, "john.doe@test.com"));
		};
	}

}
