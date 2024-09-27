import React, { useEffect, useState } from 'react';
import axios from '../Config/axiosConfig';
import '../style/ApplicationStatus.css'; // Import CSS file for styling

const ApplicationStatus = () => {
    const [applications, setApplications] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const fetchJobSeekerId = async () => {
            const storedUser = JSON.parse(localStorage.getItem('user'));
            if (storedUser?.userId) {
                try {
                    // Fetch jobSeekerId using userId
                    const jobSeekerResponse = await axios.get(`http://localhost:8080/api/jobseekers/GetJobSeekerIdByUserId/${storedUser.userId}`);
                    if (jobSeekerResponse.status === 200) {
                        const jobSeekerId = jobSeekerResponse.data;
                        
                        // Fetch applications using jobSeekerId
                        const applicationsResponse = await axios.get(`http://localhost:8080/api/applications/GetByJobSeekerId/${jobSeekerId}`);
                        setApplications(applicationsResponse.data); // Access the correct data from the response
                        setLoading(false);
                    } else {
                        console.error('Job Seeker ID not found.');
                        setLoading(false);
                    }
                } catch (error) {
                    console.error("There was an error fetching the applications!", error.response || error.message || error);
                    setLoading(false);
                }
            } else {
                console.error('User ID is not available.');
                setLoading(false);
            }
        };

        fetchJobSeekerId();
    }, []);

    if (loading) {
        return <div>Loading...</div>;
    }

    return (
        <div className="applications-container">
            <h2>My Applications</h2>
            {applications.length > 0 ? (
                <table className="applications-table">
                    <thead>
                        <tr>
                            <th>Application ID</th>
                            <th>Job ID</th>
                            <th>Status</th>
                            <th>Created At</th>
                            <th>Updated At</th>
                        </tr>
                    </thead>
                    <tbody>
                        {applications.map(application => (
                            <tr key={application.applicationId}>
                                <td>{application.applicationId}</td>
                                <td>{application.jobId}</td>
                                <td>{application.status}</td>
                                <td>{new Date(application.createdAt).toLocaleDateString()}</td>
                                <td>{new Date(application.updatedAt).toLocaleDateString()}</td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            ) : (
                <p>No applications found.</p>
            )}
        </div>
    );
};

export default ApplicationStatus;
