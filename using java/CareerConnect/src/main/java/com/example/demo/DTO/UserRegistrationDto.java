package com.example.demo.DTO;
//package com.careerconnect.dto;

public class UserRegistrationDto {
    private String email;
    private String password;
    private String role;
    private JobSeekerDto jobSeeker;
    private EmployerDto employer;
    private String profilePicture;

    // Getters and Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public JobSeekerDto getJobSeeker() {
        return jobSeeker;
    }

    public void setJobSeeker(JobSeekerDto jobSeeker) {
        this.jobSeeker = jobSeeker;
    }

    public EmployerDto getEmployer() {
        return employer;
    }

    public void setEmployer(EmployerDto employer) {
        this.employer = employer;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }
}