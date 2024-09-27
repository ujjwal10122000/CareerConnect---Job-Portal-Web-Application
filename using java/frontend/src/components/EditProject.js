import React, { useState, useEffect } from 'react';
import axios from '../Config/axiosConfig';
import { useNavigate, useParams } from 'react-router-dom';
import '../style/EditProject.css';

const EditProject = () => {
  const { projectId } = useParams();
  const [projectName, setProjectName] = useState('');
  const [description, setDescription] = useState('');
  const [technologies, setTechnologies] = useState('');
  const [startDate, setStartDate] = useState('');
  const [endDate, setEndDate] = useState('');
  const [jobSeekerId, setJobSeekerId] = useState(null);

  const navigate = useNavigate();

  useEffect(() => {
    axios.get(`http://localhost:8080/api/projects/GetById/${projectId}`)
      .then(response => {
        const projectData = response.data;
        setProjectName(projectData.projectName);
        setDescription(projectData.description);
        setTechnologies(projectData.technologies);
        setStartDate(new Date(projectData.startDate).toISOString().split('T')[0]);
        setEndDate(new Date(projectData.endDate).toISOString().split('T')[0]);
        setJobSeekerId(projectData.jobSeekerId); // Set the jobSeekerId
      })
      .catch(error => {
        console.error('Error fetching project:', error);
      });
  }, [projectId]);

  const handleUpdateProject = (event) => {
    event.preventDefault();
    const updatedProject = {
      projectId,
      jobSeekerId, // Include jobSeekerId in the request body
      projectName,
      description,
      technologies,
      startDate,
      endDate
    };
    axios.put(`http://localhost:8080/api/projects/Update/${projectId}`, updatedProject)
      .then(response => {
        console.log('Project updated:', response.data);
        navigate('/projects'); // Redirect to JobSeekerProjects page after update
      })
      .catch(error => {
        console.error('Error updating project:', error);
      });
  };

  return (
    <div>
      <h1>Edit Project</h1>
      <form onSubmit={handleUpdateProject}>
        <div>
          <label>Project Name:</label>
          <input
            type="text"
            value={projectName}
            onChange={(event) => setProjectName(event.target.value)}
          />
        </div>
        <div>
          <label>Description:</label>
          <textarea
            value={description}
            onChange={(event) => setDescription(event.target.value)}
          />
        </div>
        <div>
          <label>Technologies:</label>
          <input
            type="text"
            value={technologies}
            onChange={(event) => setTechnologies(event.target.value)}
          />
        </div>
        <div>
          <label>Start Date:</label>
          <input
            type="date"
            value={startDate}
            onChange={(event) => setStartDate(event.target.value)}
          />
        </div>
        <div>
          <label>End Date:</label>
          <input
            type="date"
            value={endDate}
            onChange={(event) => setEndDate(event.target.value)}
          />
        </div>
        <button type="submit">Update Project</button>
      </form>
    </div>
  );
};

export default EditProject;
