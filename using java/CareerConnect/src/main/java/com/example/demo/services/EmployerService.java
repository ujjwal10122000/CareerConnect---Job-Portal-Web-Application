package com.example.demo.services;
//package com.careerconnect.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Models.Employer;
import com.example.demo.repository.EmployerRepository;

@Service
public class EmployerService {

    @Autowired
    private EmployerRepository employerRepository;

    // Method to fetch all employers
    public List<Employer> getAllEmployers() {
        return employerRepository.findAll();
    }

    // Method to fetch employer by userId
    public Optional<Employer> getEmployerByUserId(int userId) {
        return employerRepository.findByUser_UserId(userId);
    }
    
    // Method to get employer ID by userId
    public Optional<Integer> getEmployerIdByUserId(int userId) {
        return employerRepository.findByUser_UserId(userId)
                .map(Employer::getEmployerId); // Assuming `getEmployerId` returns the ID of the employer
    }
    
    // Method to create an employer
    public Employer createEmployer(Employer employer) {
        LocalDateTime now = LocalDateTime.now();
        employer.setCreatedAt(now);
        employer.setUpdatedAt(now);
        return employerRepository.save(employer);
    }

    // Method to update an employer
    public Employer updateEmployer(int userId, Employer updatedEmployer) {
        Optional<Employer> existingEmployer = employerRepository.findByUser_UserId(userId);
        if (existingEmployer.isPresent()) {
            Employer employer = existingEmployer.get();
            if (updatedEmployer.getCompanyName() != null) {
                employer.setCompanyName(updatedEmployer.getCompanyName());
            }
            if (updatedEmployer.getCompanyDescription() != null) {
                employer.setCompanyDescription(updatedEmployer.getCompanyDescription());
            }
            employer.setUpdatedAt(LocalDateTime.now());
            return employerRepository.save(employer);
        }
        return null;
    }

    // Method to delete an employer
    public void deleteEmployer(int userId) {
        Optional<Employer> employer = employerRepository.findByUser_UserId(userId);
        employer.ifPresent(e -> employerRepository.delete(e));
    }

    // Check if an employer exists by userId
    public boolean employerExists(int userId) {
        return employerRepository.findByUser_UserId(userId).isPresent();
    }
}
