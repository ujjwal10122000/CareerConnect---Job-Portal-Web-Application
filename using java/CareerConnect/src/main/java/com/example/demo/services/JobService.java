package com.example.demo.services;
//package com.careerconnect.services;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Models.Job;
import com.example.demo.repository.JobRepository;

@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;

    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }

    public Optional<Job> getJobById(int id) {
        return jobRepository.findById(id);
    }

    public List<Job> getJobsByEmployerId(int employerId) {
        return jobRepository.findByEmployerId(employerId);
    }

    public List<Job> filterJobs(String jobTitle, String location, String jobType, String experienceLevel, String industry, BigDecimal minSalary, BigDecimal maxSalary) {
        return jobRepository.findAll().stream()
                .filter(job -> (jobTitle == null || job.getJobTitle().contains(jobTitle)) &&
                        (location == null || job.getLocation().contains(location)) &&
                        (jobType == null || job.getJobType().contains(jobType)) &&
                        (experienceLevel == null || job.getExperienceLevel().contains(experienceLevel)) &&
                        (industry == null || job.getIndustry().contains(industry)) &&
                        (minSalary == null || job.getSalary().compareTo(minSalary) >= 0) &&
                        (maxSalary == null || job.getSalary().compareTo(maxSalary) <= 0))
                .collect(Collectors.toList());
    }

    public Job createJob(Job job) {
        LocalDateTime now = LocalDateTime.now();
        job.setCreatedAt(now);
        job.setUpdatedAt(now);
        return jobRepository.save(job);
    }

    public Job updateJob(int id, Job job) {
        Optional<Job> existingJob = jobRepository.findById(id);
        if (existingJob.isPresent()) {
            Job existing = existingJob.get();
            // Update relevant fields
            existing.setUpdatedAt(LocalDateTime.now());
            return jobRepository.save(existing);
        }
        return null; // or throw an exception if the job is not found
    }

    public void deleteJob(int id) {
        jobRepository.deleteById(id);
    }

    public boolean jobExists(int id) {
        return jobRepository.existsById(id);
    }
}