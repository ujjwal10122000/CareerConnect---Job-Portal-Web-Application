package com.example.demo.Controllers;
//package com.careerconnect.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.DTO.ProfilePictureResponseDto;
import com.example.demo.DTO.UserLoginDto;
import com.example.demo.DTO.UserRegistrationDto;
import com.example.demo.DTO.UserSignInResponseDto;
import com.example.demo.Models.User;
import com.example.demo.security.JwtConstant;
import com.example.demo.services.UserService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/getall")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<User> getUser(@PathVariable int id) {
        Optional<User> user = userService.getUserById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRegistrationDto registrationDto) {
        try {
            User createdUser = userService.registerUser(registrationDto);
            return ResponseEntity.ok(createdUser);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@RequestBody UserLoginDto signInDto) {
        Optional<User> user = userService.signIn(signInDto.getEmail(), signInDto.getPassword());
        
        if (user.isPresent()) {
            UserSignInResponseDto responseDto = new UserSignInResponseDto();
            responseDto.setUserId(user.get().getUserId());
            responseDto.setEmail(user.get().getEmail());
            responseDto.setProfilePictureUrl(user.get().getProfilePicture());
            responseDto.setRole(user.get().getRole());

            // Generate the JWT token
            SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());
            String jwtToken = Jwts.builder()
                    .setSubject(user.get().getEmail())
                    .claim("email", user.get().getEmail())
                    .claim("authorities", user.get().getRole())
                    .signWith(key)
                    .compact();

            // Add the JWT token to the response
            responseDto.setJwtToken(jwtToken);

            return ResponseEntity.ok(responseDto);
        } else {
            return ResponseEntity.status(401).body("Invalid email or password.");
        }
    }

    @PostMapping("/upload-profile-picture/{id}")
    public ResponseEntity<?> uploadProfilePicture(@PathVariable int id, @RequestParam("file") MultipartFile profilePicture) {
        try {
            userService.uploadProfilePicture(id, profilePicture);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Failed to upload profile picture " + e);
        }
    }

    @GetMapping("/{id}/profile-picture")
    public ResponseEntity<?> getUserProfilePicture(@PathVariable int id) {
        String profilePictureUrl = userService.getUserProfilePicture(id);
        
        // Create an instance of ProfilePictureResponseDto
        ProfilePictureResponseDto responseDto = new ProfilePictureResponseDto();
        // Use setter method to set profile picture URL
        responseDto.setProfilePictureUrl(profilePictureUrl);
        
        return ResponseEntity.ok(responseDto);
    }
    
    
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable int id, @RequestBody User user) {
        try {
            userService.updateUser(id, user);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable int id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}