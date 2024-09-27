package com.example.demo.DTO;
//package com.careerconnect.dto;

public class ProfilePictureResponseDto {
    private String profilePictureUrl;

    // Default constructor
    public ProfilePictureResponseDto() {
    }

    // Constructor with parameter
    public ProfilePictureResponseDto(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }

    // Getter
    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    // Setter
    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }


}