package com.cause15.issuetrackerserver.service;

import com.cause15.issuetrackerserver.model.Project;
import com.cause15.issuetrackerserver.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;


    public Project createProject(Project project) {
        return projectRepository.save(project);
    }

    // Get a project by ID
    public Optional<Project> getProjectById(UUID id) {
        return projectRepository.findById(id);
    }

    // Get all projects
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    // Update a project
    public Project updateProject(UUID id, Project updatedProject) {
        Optional<Project> existingProjectOpt = projectRepository.findById(id);

        if (existingProjectOpt.isPresent()) {
            Project existingProject = existingProjectOpt.get();
            existingProject.setName(updatedProject.getName());
            existingProject.setIssueIds(updatedProject.getIssueIds());
            existingProject.setUserIds(updatedProject.getUserIds());

            return projectRepository.save(existingProject);
        } else {
            throw new RuntimeException("Project not found with id " + id);
        }
    }

    // Delete a project by ID
    public void deleteProject(UUID id) {
        projectRepository.deleteById(id);
    }
}
