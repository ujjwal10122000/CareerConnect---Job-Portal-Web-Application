import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from '../Config/axiosConfig';

const EditJobSeekerProfile = () => {
    const [jobSeeker, setJobSeeker] = useState({
        firstName: '',
        lastName: '',
        description: '',
        skills: '',
    });
    const [jobSeekerId, setJobSeekerId] = useState(null); // State to hold the JobSeeker ID
    const [loading, setLoading] = useState(true);
    const userId = JSON.parse(localStorage.getItem('user'))?.userId;
    const navigate = useNavigate();

    useEffect(() => {
        if (userId) {
            // Fetch JobSeeker ID by User ID
            axios.get(`http://localhost:8080/api/jobseekers/GetJobSeekerIdByUserId/${userId}`)
                .then(response => {
                    setJobSeekerId(response.data);
                    return axios.get(`http://localhost:8080/api/jobseekers/GetById/${response.data}`);
                })
                .then(response => {
                    setJobSeeker(response.data);
                })
                .catch(error => {
                    console.error('Error fetching job seeker:', error);
                })
                .finally(() => setLoading(false));
        }
    }, [userId]);

    const handleChange = (e) => {
        setJobSeeker({
            ...jobSeeker,
            [e.target.name]: e.target.value,
        });
    };

    const handleSubmit = (e) => {
        e.preventDefault();

        axios.put(`http://localhost:8080/api/jobseekers/Update/${jobSeekerId}`, jobSeeker)
            .then(response => {
                if (response.status === 200) {
                    navigate('/profile');
                } else {
                    console.error('Failed to update job seeker profile');
                }
            })
            .catch(error => console.error('Error updating job seeker profile:', error));
    };

    if (loading) return <p>Loading...</p>;

    return (
        <div className="container mt-4">
            <h2>Edit Profile</h2>
            <form onSubmit={handleSubmit}>
                <div className="form-group">
                    <label htmlFor="firstName">First Name</label>
                    <input
                        type="text"
                        className="form-control"
                        id="firstName"
                        name="firstName"
                        value={jobSeeker.firstName}
                        onChange={handleChange}
                        required
                    />
                </div>
                <div className="form-group">
                    <label htmlFor="lastName">Last Name</label>
                    <input
                        type="text"
                        className="form-control"
                        id="lastName"
                        name="lastName"
                        value={jobSeeker.lastName}
                        onChange={handleChange}
                        required
                    />
                </div>
                <div className="form-group">
                    <label htmlFor="description">Description</label>
                    <textarea
                        className="form-control"
                        id="description"
                        name="description"
                        value={jobSeeker.description}
                        onChange={handleChange}
                        required
                    />
                </div>
                <div className="form-group">
                    <label htmlFor="skills">Skills</label>
                    <input
                        type="text"
                        className="form-control"
                        id="skills"
                        name="skills"
                        value={jobSeeker.skills}
                        onChange={handleChange}
                        required
                    />
                </div>
                <button type="submit" className="btn btn-primary">Save</button>
            </form>
        </div>
    );
};

export default EditJobSeekerProfile;
