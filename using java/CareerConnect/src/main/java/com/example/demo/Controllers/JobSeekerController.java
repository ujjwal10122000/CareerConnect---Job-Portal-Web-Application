package com.example.demo.Controllers;
//package com.careerconnect.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Models.JobSeeker;
import com.example.demo.services.JobSeekerService;

@RestController
@RequestMapping("/api/jobseekers")
public class JobSeekerController {

    @Autowired
    private JobSeekerService jobSeekerService;

    @GetMapping("/GetAll")
    public List<JobSeeker> getAllJobSeekers() {
        return jobSeekerService.getAllJobSeekers();
    }

    @GetMapping("/GetById/{id}")
    public ResponseEntity<JobSeeker> getJobSeeker(@PathVariable int id) {
        Optional<JobSeeker> jobSeeker = jobSeekerService.getJobSeekerById(id);
        return jobSeeker.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/Update/{id}")
    public ResponseEntity<JobSeeker> updateJobSeeker(@PathVariable int id, @RequestBody JobSeeker jobSeeker) {
//        if (id != jobSeeker.getJobSeekerId()) {
//            return ResponseEntity.badRequest().build();
//        }

        if (!jobSeekerService.jobSeekerExists(id)) {
            return ResponseEntity.notFound().build();
        }

        JobSeeker updatedJobSeeker = jobSeekerService.updateJobSeeker(id, jobSeeker);
        return ResponseEntity.ok(updatedJobSeeker);
    }

    @PostMapping("/Create")
    public ResponseEntity<JobSeeker> createJobSeeker(@RequestBody JobSeeker jobSeeker) {
        JobSeeker newJobSeeker = jobSeekerService.createJobSeeker(jobSeeker);
        return ResponseEntity.ok(newJobSeeker);
    }
    
    @GetMapping("/GetJobSeekerIdByUserId/{userId}")
    public ResponseEntity<Integer> getJobSeekerIdByUserId(@PathVariable int userId) {
        Optional<Integer> jobSeekerId = jobSeekerService.getJobSeekerIdByUserId(userId);
        return jobSeekerId.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/Delete/{id}")
    public ResponseEntity<Void> deleteJobSeeker(@PathVariable int id) {
        if (!jobSeekerService.jobSeekerExists(id)) {
            return ResponseEntity.notFound().build();
        }

        jobSeekerService.deleteJobSeeker(id);
        return ResponseEntity.noContent().build();
    }
}