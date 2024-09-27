import React, { useEffect, useState } from 'react';
import axios from '../Config/axiosConfig';
import { Link } from 'react-router-dom';
import '../style/EmployerJobs.css'; // Import CSS file for styling

const EmployerJobs = () => {
    const [jobs, setJobs] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [employerId, setEmployerId] = useState(null);

    const userId = JSON.parse(localStorage.getItem('user')).userId;

    useEffect(() => {
        const fetchEmployerId = async () => {
            try {
                const response = await axios.get(`http://localhost:8080/api/employers/GetEmployerIdByUserId/${userId}`);
                console.log('Employer ID:', response.data);
                setEmployerId(response.data);
            } catch (error) {
                console.error('Error fetching employer ID:', error.response || error.message || error);
                setError('Error fetching employer ID.');
                setLoading(false);
            }
        };

        fetchEmployerId();
    }, [userId]);

    useEffect(() => {
        if (employerId !== null) {
            console.log('Fetching jobs for employerId:', employerId);
            axios.get(`http://localhost:8080/api/jobs/GetByEmployerId/${employerId}`)
                .then(response => {
                    console.log('Jobs data:', response.data);
                    setJobs(response.data); // Use the correct path to the jobs data
                    setLoading(false);
                })
                .catch(error => {
                    console.error('Error fetching jobs:', error.response || error.message || error);
                    setError('There was an error fetching the jobs.');
                    setLoading(false);
                });
        }
    }, [employerId]);

    if (loading) {
        return <div>Loading...</div>;
    }

    if (error) {
        return <div>{error}</div>;
    }

    return (
        <div className="jobs-container">
            <h2>My Jobs</h2>
            {jobs.length > 0 ? (
                <div className="cards-container">
                    {jobs.map(job => (
                        <div className="job-card" key={job.jobId}>
                            <h3>{job.jobTitle}</h3>
                            <p>{job.jobDescription}</p>
                            <p><strong>Location:</strong> {job.location || 'N/A'}</p>
                            <p><strong>Salary:</strong> ${job.salary.toLocaleString() || 'N/A'}</p>
                            <p><strong>Job Type:</strong> {job.jobType || 'N/A'}</p>
                            <p><strong>Experience Level:</strong> {job.experienceLevel || 'N/A'}</p>
                            <p><strong>Industry:</strong> {job.industry || 'N/A'}</p>
                            <p><strong>Created At:</strong> {new Date(job.createdAt).toLocaleDateString() || 'N/A'}</p>
                            {/* <p><strong>Updated At:</strong> {new Date(job.updatedAt).toLocaleDateString() || 'N/A'}</p> */}
                            {/* Edit Job Button */}
                            <Link to={`/edit-job/${job.jobId}`} className="btn btn-primary">Edit Job</Link>
                            {/* View Applications Button */}
                            <Link to={`/view-applications/${job.jobId}`} className="btn btn-secondary">View Applications</Link>
                        </div>
                    ))}
                </div>
            ) : (
                <p>No jobs found.</p>
            )}
        </div>
    );
};

export default EmployerJobs;
