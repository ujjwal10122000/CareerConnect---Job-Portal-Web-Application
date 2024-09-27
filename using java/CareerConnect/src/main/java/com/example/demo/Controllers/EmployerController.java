package com.example.demo.Controllers;
//package com.careerconnect.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.Models.Employer;
import com.example.demo.services.EmployerService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/employers")
public class EmployerController {

    @Autowired
    private EmployerService employerService;

    @GetMapping("/GetAll")
    public List<Employer> getAllEmployers() {
        return employerService.getAllEmployers();
    }
    
    
    @GetMapping("/GetEmployerIdByUserId/{userId}")
    public ResponseEntity<Integer> getEmployerIdByUserId(@PathVariable int userId) {
        Optional<Integer> employerId = employerService.getEmployerIdByUserId(userId);
        return employerId.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @GetMapping("/GetByUserId/{userId}")
    public ResponseEntity<Employer> getEmployerByUserId(@PathVariable int userId) {
        Optional<Employer> employer = employerService.getEmployerByUserId(userId);
        return employer.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/Update/{userId}")
    public ResponseEntity<Employer> updateEmployer(@PathVariable int userId, @RequestBody Employer employerDto) {
        Employer updatedEmployer = employerService.updateEmployer(userId, employerDto);
        if (updatedEmployer != null) {
            return ResponseEntity.ok(updatedEmployer);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/Create")
    public ResponseEntity<Employer> createEmployer(@RequestBody Employer employer) {
        Employer newEmployer = employerService.createEmployer(employer);
        return ResponseEntity.ok(newEmployer);
    }

    @DeleteMapping("/Delete/{userId}")
    public ResponseEntity<Void> deleteEmployer(@PathVariable int userId) {
        if (employerService.employerExists(userId)) {
            employerService.deleteEmployer(userId);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
