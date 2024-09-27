package com.example.demo.Models;
//package com.example.careerconnect.models;


import java.time.LocalDateTime;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int applicationId;

    private int jobId;
    private int jobSeekerId;
    private String status;
    private String coverLetter;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "jobId", insertable = false, updatable = false)
    private Job job;

    @ManyToOne
    @JoinColumn(name = "jobSeekerId", insertable = false, updatable = false)
    private JobSeeker jobSeeker;

	public int getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(int applicationId) {
		this.applicationId = applicationId;
	}

	public int getJobId() {
		return jobId;
	}

	public void setJobId(int jobId) {
		this.jobId = jobId;
	}

	public int getJobSeekerId() {
		return jobSeekerId;
	}

	public void setJobSeekerId(int jobSeekerId) {
		this.jobSeekerId = jobSeekerId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCoverLetter() {
		return coverLetter;
	}

	public void setCoverLetter(String coverLetter) {
		this.coverLetter = coverLetter;
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

	public void setUpdatedAt(LocalDateTime date) {
		this.updatedAt = date;
	}

	public Job getJob() {
		return job;
	}

	public void setJob(Job job) {
		this.job = job;
	}

	public JobSeeker getJobSeeker() {
		return jobSeeker;
	}

	public void setJobSeeker(JobSeeker jobSeeker) {
		this.jobSeeker = jobSeeker;
	}

	@Override
	public String toString() {
		return "Application [applicationId=" + applicationId + ", jobId=" + jobId + ", jobSeekerId=" + jobSeekerId
				+ ", status=" + status + ", coverLetter=" + coverLetter + ", createdAt=" + createdAt + ", updatedAt="
				+ updatedAt + ", job=" + job + ", jobSeeker=" + jobSeeker + ", getApplicationId()=" + getApplicationId()
				+ ", getJobId()=" + getJobId() + ", getJobSeekerId()=" + getJobSeekerId() + ", getStatus()="
				+ getStatus() + ", getCoverLetter()=" + getCoverLetter() + ", getCreatedAt()=" + getCreatedAt()
				+ ", getUpdatedAt()=" + getUpdatedAt() + ", getJob()=" + getJob() + ", getJobSeeker()=" + getJobSeeker()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}

   
}
