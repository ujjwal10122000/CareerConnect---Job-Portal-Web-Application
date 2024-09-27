package com.example.demo.Models;
//package com.example.careerconnect.models;


import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int projectId;

    private int jobSeekerId;
    private String projectName;
    private String description;
    private String technologies;
    private LocalDate startDate;
    private LocalDate endDate;

    @ManyToOne
    @JoinColumn(name = "jobSeekerId", insertable = false, updatable = false)
    private JobSeeker jobSeeker;

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public int getJobSeekerId() {
		return jobSeekerId;
	}

	public void setJobSeekerId(int jobSeekerId) {
		this.jobSeekerId = jobSeekerId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTechnologies() {
		return technologies;
	}

	public void setTechnologies(String technologies) {
		this.technologies = technologies;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public JobSeeker getJobSeeker() {
		return jobSeeker;
	}

	public void setJobSeeker(JobSeeker jobSeeker) {
		this.jobSeeker = jobSeeker;
	}

	@Override
	public String toString() {
		return "Project [projectId=" + projectId + ", jobSeekerId=" + jobSeekerId + ", projectName=" + projectName
				+ ", description=" + description + ", technologies=" + technologies + ", startDate=" + startDate
				+ ", endDate=" + endDate + ", jobSeeker=" + jobSeeker + ", getProjectId()=" + getProjectId()
				+ ", getJobSeekerId()=" + getJobSeekerId() + ", getProjectName()=" + getProjectName()
				+ ", getDescription()=" + getDescription() + ", getTechnologies()=" + getTechnologies()
				+ ", getStartDate()=" + getStartDate() + ", getEndDate()=" + getEndDate() + ", getJobSeeker()="
				+ getJobSeeker() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}

    // Getters and Setters
}