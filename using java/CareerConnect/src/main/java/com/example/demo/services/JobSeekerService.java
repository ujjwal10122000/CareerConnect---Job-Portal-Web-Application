package com.example.demo.services;
//package com.careerconnect.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Models.JobSeeker;
import com.example.demo.repository.JobSeekerRepository;

@Service
public class JobSeekerService {

    @Autowired
    private JobSeekerRepository jobSeekerRepository;

    public List<JobSeeker> getAllJobSeekers() {
        return jobSeekerRepository.findAll();
    }

    public Optional<JobSeeker> getJobSeekerById(int id) {
        return jobSeekerRepository.findById(id);
    }

    public Optional<Integer> getJobSeekerIdByUserId(int userId) {
        return jobSeekerRepository.findByUser_UserId(userId)
                .map(JobSeeker::getJobSeekerId); // Assuming `getJobSeekerId` returns the ID of the JobSeeker
    }
    
    public JobSeeker createJobSeeker(JobSeeker jobSeeker) {
        LocalDateTime now = LocalDateTime.now();
        jobSeeker.setCreatedAt(now);
        jobSeeker.setUpdatedAt(now);
        return jobSeekerRepository.save(jobSeeker);
    }

    public JobSeeker updateJobSeeker(int id, JobSeeker updatedJobSeeker) {
        Optional<JobSeeker> existingJobSeeker = jobSeekerRepository.findById(id);
        if (existingJobSeeker.isPresent()) {
            JobSeeker jobSeeker = existingJobSeeker.get();
            // Update relevant fields
            jobSeeker.setUpdatedAt(LocalDateTime.now());
            return jobSeekerRepository.save(jobSeeker);
        }
        return null; // or throw an exception if JobSeeker not found
    }

    public void deleteJobSeeker(int id) {
        jobSeekerRepository.deleteById(id);
    }

    public boolean jobSeekerExists(int id) {
        return jobSeekerRepository.existsById(id);
    }
}