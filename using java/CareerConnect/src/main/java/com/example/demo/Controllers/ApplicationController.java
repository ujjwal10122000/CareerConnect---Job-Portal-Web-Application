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

import com.example.demo.Models.Application;
import com.example.demo.services.ApplicationService;



@RestController
@RequestMapping("/api/applications")
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    @GetMapping("/GetAll")
    public List<Application> getAllApplications() {
        return applicationService.getAllApplications();
    }

    @GetMapping("/GetById/{id}")
    public ResponseEntity<Application> getApplicationById(@PathVariable int id) {
        Optional<Application> application = applicationService.getApplicationById(id);
        return application.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/Update/{id}")
    public ResponseEntity<Application> updateApplication(@PathVariable int id, @RequestBody Application application) {
        if (id != application.getApplicationId()) {
            return ResponseEntity.badRequest().build();
        }
        Application updatedApplication = applicationService.updateApplication(id, application);
        return ResponseEntity.ok(updatedApplication);
    }

    @PostMapping("/Create")
    public ResponseEntity<Application> createApplication(@RequestBody Application application) {
        Application newApplication = applicationService.createApplication(application);
        return ResponseEntity.ok(newApplication);
    }

    @DeleteMapping("/Delete/{id}")
    public ResponseEntity<Void> deleteApplication(@PathVariable int id) {
        applicationService.deleteApplication(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/GetByJobSeekerId/{jobSeekerId}")
    public List<Application> getApplicationsByJobSeekerId(@PathVariable int jobSeekerId) {
        return applicationService.getApplicationsByJobSeekerId(jobSeekerId);
    }

    @GetMapping("/GetByJobId/{jobId}")
    public List<Application> getApplicationsByJobId(@PathVariable int jobId) {
        return applicationService.getApplicationsByJobId(jobId);
    }

    @PutMapping("/UpdateStatus/{id}")
    public ResponseEntity<Application> updateApplicationStatus(@PathVariable int id, @RequestBody String status) {
        Application updatedApplication = applicationService.updateApplicationStatus(id, status);
        return ResponseEntity.ok(updatedApplication);
    }
}
