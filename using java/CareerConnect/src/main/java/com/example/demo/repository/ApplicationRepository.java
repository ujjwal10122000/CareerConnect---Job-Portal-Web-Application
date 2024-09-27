package com.example.demo.repository;
//package com.example.careerconnect.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Models.Application;



public interface ApplicationRepository extends JpaRepository<Application, Integer> {
    List<Application> findByJobSeekerId(int jobSeekerId);
    List<Application> findByJobId(int jobId);
}