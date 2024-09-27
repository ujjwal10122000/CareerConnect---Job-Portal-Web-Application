package com.example.demo.repository;
//package com.example.careerconnect.repositories;




import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Models.Employer;

public interface EmployerRepository extends JpaRepository<Employer, Integer> {
	Optional<Employer> findByUser_UserId(int userId);
}