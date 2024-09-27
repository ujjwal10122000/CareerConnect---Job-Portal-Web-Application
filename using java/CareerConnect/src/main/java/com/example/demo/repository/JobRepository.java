package com.example.demo.repository;
//package com.example.careerconnect.repositories;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Models.Job;



@Repository
public interface JobRepository extends JpaRepository<Job, Integer> {
    List<Job> findByEmployerId(int employerId);
}