package com.example.demo.repository;
//package com.example.careerconnect.repositories;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Models.Project;


public interface ProjectRepository extends JpaRepository<Project, Integer> {
    List<Project> findByJobSeekerId(int jobSeekerId);
}
