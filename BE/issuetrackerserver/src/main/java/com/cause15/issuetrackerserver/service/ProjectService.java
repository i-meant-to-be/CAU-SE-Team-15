package com.cause15.issuetrackerserver.service;

import com.cause15.issuetrackerserver.model.Project;
import com.cause15.issuetrackerserver.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProjectService {
    // Dependency injection
    private final ProjectRepository projectRepository;
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    // Methods
    // Create a project
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
            existingProject.setTitle(updatedProject.getTitle());
            existingProject.setIssueIds(updatedProject.getIssueIds());
            existingProject.setUserIds(updatedProject.getUserIds());

            return projectRepository.save(existingProject);
        } else {
            throw new RuntimeException("Project not found with id " + id);
        }
    }

    // Delete a project by ID
    public boolean deleteProject(UUID id) {
        if (projectRepository.existsById(id)) {
            projectRepository.deleteById(id);
            return true;
        }
        else return false;
    }
}
