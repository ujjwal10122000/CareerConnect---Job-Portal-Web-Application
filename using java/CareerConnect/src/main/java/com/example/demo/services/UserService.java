package com.example.demo.services;
//package com.careerconnect.services;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.DTO.UserRegistrationDto;
import com.example.demo.Models.Employer;
import com.example.demo.Models.JobSeeker;
import com.example.demo.Models.User;
import com.example.demo.repository.EmployerRepository;
import com.example.demo.repository.JobSeekerRepository;
import com.example.demo.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JobSeekerRepository jobSeekerRepository;

    @Autowired
    private EmployerRepository employerRepository;

    private final String UPLOAD_DIR = "C:\\Users\\HP\\Desktop\\New folder";

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(int id) {
        return userRepository.findById(id);
    }

    public User registerUser(UserRegistrationDto registrationDto) {
        User user = new User();
        user.setEmail(registrationDto.getEmail());
        user.setPassword(registrationDto.getPassword());
        user.setRole(registrationDto.getRole());
        user.setProfilePicture(registrationDto.getProfilePicture());

        // Save User first to generate its ID
        userRepository.save(user);

        if ("JobSeeker".equals(registrationDto.getRole())) {
            if (registrationDto.getJobSeeker() == null) {
                throw new IllegalArgumentException("JobSeeker data is required for JobSeeker role.");
            }

            JobSeeker jobSeeker = new JobSeeker();
            jobSeeker.setFirstName(registrationDto.getJobSeeker().getFirstName());
            jobSeeker.setLastName(registrationDto.getJobSeeker().getLastName());
            jobSeeker.setDescription(registrationDto.getJobSeeker().getDescription());
            jobSeeker.setSkills(registrationDto.getJobSeeker().getSkills());
            jobSeeker.setUser(user);

            // Save JobSeeker after User has been saved
            jobSeekerRepository.save(jobSeeker);
            user.setJobSeeker(jobSeeker);
        } else if ("Employer".equals(registrationDto.getRole())) {
            if (registrationDto.getEmployer() == null) {
                throw new IllegalArgumentException("Employer data is required for Employer role.");
            }

            Employer employer = new Employer();
            employer.setCompanyName(registrationDto.getEmployer().getCompanyName());
            employer.setCompanyDescription(registrationDto.getEmployer().getCompanyDescription());
            employer.setUser(user);

            // Save Employer after User has been saved
            employerRepository.save(employer);
            user.setEmployer(employer);
        } else {
            throw new IllegalArgumentException("Invalid role.");
        }

        // Update the User with its associated JobSeeker or Employer
        return userRepository.save(user);
    }







    public Optional<User> signIn(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password);
    }

    public void uploadProfilePicture(int id, MultipartFile profilePicture) throws IOException {
        User user = userRepository.findById(id).orElseThrow();
        if (profilePicture != null && !profilePicture.isEmpty()) {
            String fileName = System.currentTimeMillis() + "_" + profilePicture.getOriginalFilename();
            File directory = new File(UPLOAD_DIR);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            File file = new File(directory, fileName);
            profilePicture.transferTo(file);
            user.setProfilePicture(fileName);
            user.setUpdatedAt(LocalDateTime.now());
            userRepository.save(user);
        }
    }

    public String getUserProfilePicture(int id) {
        User user = userRepository.findById(id).orElseThrow();
        String profilePictureFileName = user.getProfilePicture();
        String profilePictureUrl = "http://localhost:8080/uploads/" + profilePictureFileName;  // Construct full URL
        return profilePictureUrl;
    }

    public void updateUser(int id, User user) {
        user.setUserId(id);
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);
    }

    public void deleteUser(int id) {
        User user = userRepository.findById(id).orElseThrow();
//        if (user.getJobSeeker() != null) {
//            jobSeekerRepository.delete(user.getJobSeeker());
//        }
//        if (user.getEmployer() != null) {
//            employerRepository.delete(user.getEmployer());
//        }
        userRepository.delete(user);
    }
}