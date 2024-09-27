import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from '../Config/axiosConfig';
import '../style/JobSeekerProfile.css';

const JobSeekerProfile = () => {
    const [jobSeeker, setJobSeeker] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [profilePic, setProfilePic] = useState('/default-profile.png');
    const [selectedFile, setSelectedFile] = useState(null);
    const [resumeUrl, setResumeUrl] = useState(null); // State to hold the resume URL

    const user = JSON.parse(localStorage.getItem('user'));
    const userId = user?.userId;
    const navigate = useNavigate();

    // Move fetchProfilePic outside of the useEffect hook
    const fetchProfilePic = async () => {
        try {
            const response = await axios.get(`http://localhost:8080/api/users/${userId}/profile-picture`);
            let profilePictureUrl = response.data.profilePictureUrl?.replace(/\\/g, '/');
            
            // Check if the profilePictureUrl already starts with the base URL
            if (!profilePictureUrl.startsWith('http://localhost:8080')) {
                profilePictureUrl = `http://localhost:8080${profilePictureUrl}`;
            }
    
            setProfilePic(profilePictureUrl);
            console.log(profilePictureUrl); // This should now log the correct URL
        } catch (error) {
            console.error('Error fetching profile picture:', error);
        }
    };

    useEffect(() => {
        const fetchJobSeekerProfile = async (jobSeekerId) => {
            try {
                const response = await axios.get(`http://localhost:8080/api/jobseekers/GetById/${jobSeekerId}`);
                setJobSeeker(response.data);
            } catch (error) {
                setError('Error fetching job seeker profile');
                console.error('Error fetching job seeker profile:', error);
            } finally {
                setLoading(false);
            }
        };

        const fetchJobSeekerId = async () => {
            try {
                const response = await axios.get(`http://localhost:8080/api/jobseekers/GetJobSeekerIdByUserId/${userId}`);
                const jobSeekerId = response.data;
                fetchJobSeekerProfile(jobSeekerId);
            } catch (error) {
                setError('Error fetching job seeker ID');
                console.error('Error fetching job seeker ID:', error);
            }
        };

        const fetchResumeUrl = async () => {
            try {
                const response = await axios.get(`http://localhost:8080/api/JobSeekers/3/resume${userId}/resume`);
                setResumeUrl(response.data.resumeUrl); // Set the URL directly
            } catch (error) {
                console.error('Error fetching resume link:', error);
            }
        };

        if (userId) {
            fetchJobSeekerId();
            fetchProfilePic();
            fetchResumeUrl();
        }
    }, [userId]);

    const handleFileChange = (event) => {
        setSelectedFile(event.target.files[0]);
    };

    const handleChangeProfile = async () => {
        if (!selectedFile) return;
    
        const formData = new FormData();
        formData.append('file', selectedFile); // 'file' should match the @RequestParam name in your API
    
        try {
            const response = await axios.post(`http://localhost:8080/api/users/upload-profile-picture/${userId}`, formData);
            if (response.status === 200) {
                alert('Profile picture updated successfully!');
                setSelectedFile(null);
    
                // Refresh the profile picture
                await fetchProfilePic();
    
                // Reload the page to update the Navbar
                window.location.reload();
            } else {
                alert('Failed to update profile picture');
            }
        } catch (error) {
            console.error('Error uploading profile picture:', error);
            alert('Error uploading profile picture');
        }
    };
    
    const handleEditProfile = () => {
        navigate('/edit-profile');
    };

    if (loading) return <p>Loading...</p>;
    if (error) return <p>{error}</p>;
    if (!jobSeeker) return <p>No profile found</p>;

    return (
        <div className="job-seeker-profile-container">
            <div className="profile-left">
                <img src={profilePic} alt="Profile" className="profile-picture" />
                <div className="profile-actions">
                    <input type="file" onChange={handleFileChange} className="form-control-file" />
                    <button onClick={handleChangeProfile} className="btn btn-primary">Change Profile</button>
                </div>
            </div>
            <div className="profile-right">
                <h2>{`${jobSeeker.firstName} ${jobSeeker.lastName}`}</h2>
                <p><strong>Description:</strong> {jobSeeker.description}</p>
                <p><strong>Skills:</strong> {jobSeeker.skills}</p>
              
                <button onClick={handleEditProfile} className="btn btn-primary">Edit Profile</button>
            </div>
        </div>
    );
};

export default JobSeekerProfile;
