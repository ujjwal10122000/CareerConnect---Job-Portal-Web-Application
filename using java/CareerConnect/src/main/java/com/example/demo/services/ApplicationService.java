package com.example.demo.services;
//package com.careerconnect.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Models.Application;
import com.example.demo.repository.ApplicationRepository;

@Service
public class ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;

    public List<Application> getAllApplications() {
        return applicationRepository.findAll();
    }

    public Optional<Application> getApplicationById(int id) {
        return applicationRepository.findById(id);
    }

    public Application createApplication(Application application) {
        LocalDateTime now = LocalDateTime.now();
        application.setCreatedAt(now);
        application.setUpdatedAt(now);
        return applicationRepository.save(application);
    }

    public Application updateApplication(int id, Application updatedApplication) {
        Optional<Application> existingApplication = applicationRepository.findById(id);
        if (existingApplication.isPresent()) {
            Application application = existingApplication.get();
            // Copy any relevant fields from updatedApplication to application
            application.setUpdatedAt(LocalDateTime.now());
            return applicationRepository.save(application);
        }
        return null;
    }

    public void deleteApplication(int id) {
        applicationRepository.deleteById(id);
    }

    public List<Application> getApplicationsByJobSeekerId(int jobSeekerId) {
        return applicationRepository.findByJobSeekerId(jobSeekerId);
    }

    public List<Application> getApplicationsByJobId(int jobId) {
        return applicationRepository.findByJobId(jobId);
    }

    public Application updateApplicationStatus(int id, String status) {
        Optional<Application> applicationOpt = applicationRepository.findById(id);
        if (applicationOpt.isPresent()) {
            Application application = applicationOpt.get();
            application.setStatus(status);
            application.setUpdatedAt(LocalDateTime.now());
            return applicationRepository.save(application);
        }
        return null;
    }
}