package com.example.demo.Models;
//package com.example.careerconnect.models;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int jobId;

    private int employerId;
    private String jobTitle;
    private String jobDescription;
    private String location;
    private BigDecimal salary;
    private String jobType;
    private String experienceLevel;
    private String industry;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "employerId", insertable = false, updatable = false)
    @JsonIgnore
    private Employer employer;
    
    @OneToMany(mappedBy = "job")
    @JsonIgnore
    private List<Application> applications;
    
    
    
    
	public int getJobId() {
		return jobId;
	}
    
	public void setJobId(int jobId) {
		this.jobId = jobId;
	}

	public int getEmployerId() {
		return employerId;
	}

	public void setEmployerId(int employerId) {
		this.employerId = employerId;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getJobDescription() {
		return jobDescription;
	}

	public void setJobDescription(String jobDescription) {
		this.jobDescription = jobDescription;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public BigDecimal getSalary() {
		return salary;
	}

	public void setSalary(BigDecimal salary) {
		this.salary = salary;
	}

	public String getJobType() {
		return jobType;
	}

	public void setJobType(String jobType) {
		this.jobType = jobType;
	}

	public String getExperienceLevel() {
		return experienceLevel;
	}

	public void setExperienceLevel(String experienceLevel) {
		this.experienceLevel = experienceLevel;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Employer getEmployer() {
		return employer;
	}

	public void setEmployer(Employer employer) {
		this.employer = employer;
	}

	public List<Application> getApplications() {
		return applications;
	}

	public void setApplications(List<Application> applications) {
		this.applications = applications;
	}

	@Override
	public String toString() {
		return "Job [jobId=" + jobId + ", employerId=" + employerId + ", jobTitle=" + jobTitle + ", jobDescription="
				+ jobDescription + ", location=" + location + ", salary=" + salary + ", jobType=" + jobType
				+ ", experienceLevel=" + experienceLevel + ", industry=" + industry + ", createdAt=" + createdAt
				+ ", updatedAt=" + updatedAt + ", employer=" + employer + ", applications=" + applications
				+ ", getJobId()=" + getJobId() + ", getEmployerId()=" + getEmployerId() + ", getJobTitle()="
				+ getJobTitle() + ", getJobDescription()=" + getJobDescription() + ", getLocation()=" + getLocation()
				+ ", getSalary()=" + getSalary() + ", getJobType()=" + getJobType() + ", getExperienceLevel()="
				+ getExperienceLevel() + ", getIndustry()=" + getIndustry() + ", getCreatedAt()=" + getCreatedAt()
				+ ", getUpdatedAt()=" + getUpdatedAt() + ", getEmployer()=" + getEmployer() + ", getApplications()="
				+ getApplications() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}

    // Getters and Setters
}