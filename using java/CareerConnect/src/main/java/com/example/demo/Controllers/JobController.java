package com.example.demo.Controllers;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.Models.Job;
import com.example.demo.services.JobService;

@RestController
@RequestMapping("/api/jobs")
public class JobController {

    @Autowired
    private JobService jobService;

    @GetMapping("/hello")
    public String helloworld() {
        return "Hello World";
    }

    @GetMapping("/GetAll")
    public List<Job> getAllJobs() {
        return jobService.getAllJobs();
    }

    @GetMapping("/GetById/{id}")
    public ResponseEntity<Job> getJobById(@PathVariable int id) {
        Optional<Job> job = jobService.getJobById(id);
        return job.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/GetByEmployerId/{employerId}")
    public List<Job> getJobsByEmployerId(@PathVariable int employerId) {
        return jobService.getJobsByEmployerId(employerId);
    }

    @GetMapping("/Filter")
    public List<Job> filterJobs(@RequestParam(required = false) String jobTitle,
                                @RequestParam(required = false) String location,
                                @RequestParam(required = false) String jobType,
                                @RequestParam(required = false) String experienceLevel,
                                @RequestParam(required = false) String industry,
                                @RequestParam(required = false) BigDecimal minSalary,
                                @RequestParam(required = false) BigDecimal maxSalary) {
        return jobService.filterJobs(jobTitle, location, jobType, experienceLevel, industry, minSalary, maxSalary);
    }

    @PostMapping("/Create")
    public ResponseEntity<Job> createJob(@RequestBody Job job) {
        Job newJob = jobService.createJob(job);
        return ResponseEntity.ok(newJob);
    }

    @PutMapping("/Edit/{id}")
    public ResponseEntity<Job> editJob(@PathVariable int id, @RequestBody Job job) {
        if (id != job.getJobId()) {
            return ResponseEntity.badRequest().build();
        }
        Job updatedJob = jobService.updateJob(id, job);
        return updatedJob != null ? ResponseEntity.ok(updatedJob) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/Delete/{id}")
    public ResponseEntity<Void> deleteJob(@PathVariable int id) {
        jobService.deleteJob(id);
        return ResponseEntity.noContent().build();
    }
}
