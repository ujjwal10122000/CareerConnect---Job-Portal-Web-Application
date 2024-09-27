package com.example.demo.repository;
//package com.example.careerconnect.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.example.demo.Models.JobSeeker;

public interface JobSeekerRepository extends JpaRepository<JobSeeker, Integer> {
	Optional<JobSeeker> findByUser_UserId(int userId);
}