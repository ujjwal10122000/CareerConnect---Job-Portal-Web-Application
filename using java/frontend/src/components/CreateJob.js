import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from '../Config/axiosConfig';

const CreateJob = () => {
  const [jobData, setJobData] = useState({
    jobTitle: '',
    jobDescription: '',
    location: '',
    salary: '',
    jobType: '',
    experienceLevel: '',
    industry: ''
  });

  const [employerId, setEmployerId] = useState(null);
  const navigate = useNavigate();
  const user = JSON.parse(localStorage.getItem('user'));

  useEffect(() => {
    const fetchEmployerId = async () => {
      try {
        const response = await axios.get(`http://localhost:8080/api/employers/GetEmployerIdByUserId/${user.userId}`);
        setEmployerId(response.data);
      } catch (error) {
        console.error('Error fetching employer ID:', error);
      }
    };

    fetchEmployerId();
  }, [user.userId]);

  const handleChange = (e) => {
    setJobData({ ...jobData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    const jobPayload = {
      ...jobData,
      employerId: employerId,
      salary: parseFloat(jobData.salary) // Ensure salary is a number
    };

    console.log(jobPayload);

    try {
      const response = await axios.post('http://localhost:8080/api/jobs/Create', jobPayload);
      if (response.status === 201 || response.status === 200) {
        navigate('/my-jobs'); // Redirect to 'My Jobs' page after successful creation
      }
    } catch (error) {
      console.error('Error creating job:', error);
    }
  };

  return (
    <div className="container mt-5">
      <h2>Create New Job</h2>
      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <label>Job Title</label>
          <input type="text" name="jobTitle" className="form-control" value={jobData.jobTitle} onChange={handleChange} required />
        </div>
        <div className="form-group">
          <label>Job Description</label>
          <textarea name="jobDescription" className="form-control" value={jobData.jobDescription} onChange={handleChange} required />
        </div>
        <div className="form-group">
          <label>Location</label>
          <input type="text" name="location" className="form-control" value={jobData.location} onChange={handleChange} required />
        </div>
        <div className="form-group">
          <label>Salary</label>
          <input type="number" name="salary" className="form-control" value={jobData.salary} onChange={handleChange} required />
        </div>
        <div className="form-group">
          <label>Job Type</label>
          <input type="text" name="jobType" className="form-control" value={jobData.jobType} onChange={handleChange} required />
        </div>
        <div className="form-group">
          <label>Experience Level</label>
          <input type="text" name="experienceLevel" className="form-control" value={jobData.experienceLevel} onChange={handleChange} required />
        </div>
        <div className="form-group">
          <label>Industry</label>
          <input type="text" name="industry" className="form-control" value={jobData.industry} onChange={handleChange} required />
        </div>
        <button type="submit" className="btn btn-primary mt-3" disabled={!employerId}>
          Create Job
        </button>
      </form>
    </div>
  );
};

export default CreateJob;
