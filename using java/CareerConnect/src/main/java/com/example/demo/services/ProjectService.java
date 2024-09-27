package com.example.demo.services;
//package com.careerconnect.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Models.Project;
import com.example.demo.repository.ProjectRepository;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public Optional<Project> getProjectById(int id) {
        return projectRepository.findById(id);
    }

    public List<Project> getProjectsByJobSeekerId(int jobSeekerId) {
        return projectRepository.findByJobSeekerId(jobSeekerId);
    }

    public Project createProject(Project project) {
        return projectRepository.save(project);
    }

    public Project updateProject(int id, Project updatedProject) {
        if (projectRepository.existsById(id)) {
            updatedProject.setProjectId(id);
            return projectRepository.save(updatedProject);
        }
        return null;
    }

    public void deleteProject(int id) {
        projectRepository.deleteById(id);
    }
}
