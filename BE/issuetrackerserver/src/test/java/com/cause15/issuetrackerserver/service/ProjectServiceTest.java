package com.cause15.issuetrackerserver.service;

import com.cause15.issuetrackerserver.model.Project;
import com.cause15.issuetrackerserver.repository.ProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ProjectServiceTest {
    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private ProjectService projectService;

    private Project project1;
    private Project project2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        project1 = new Project();
        project1.setId(UUID.randomUUID());
        project1.setTitle("ProjectA");
        project1.setIssueIds(new ArrayList<>());
        project1.setUserIds(new ArrayList<>());

        project2 = new Project();
        project2.setId(UUID.randomUUID());
        project2.setTitle("ProjectB");
        project2.setIssueIds(new ArrayList<>());
        project2.setUserIds(new ArrayList<>());
    }
    @Test
    public void testCreateProject() {
        when(projectRepository.save(any(Project.class))).thenReturn(project1);

        Project createdProject = projectService.createProject(project1);

        assertThat(createdProject).isEqualTo(project1);
        verify(projectRepository, times(1)).save(project1);
    }

    @Test
    public void testGetProjectById() {
        when(projectRepository.findById(project1.getId())).thenReturn(Optional.of(project1));

        Optional<Project> foundProject = projectService.getProjectById(project1.getId());

        assertThat(foundProject).isPresent();
        assertThat(foundProject.get()).isEqualTo(project1);
        verify(projectRepository, times(1)).findById(project1.getId());
    }

    @Test
    void testGetAllProjects(){
        Project project = new Project("test", "asd");
        projectService.getAllProjects();
    }
    @Test
    public void testUpdateProject() {
        when(projectRepository.findById(project1.getId())).thenReturn(Optional.of(project1));
        when(projectRepository.save(any(Project.class))).thenReturn(project1);

        Project updatedProject = new Project();
        updatedProject.setTitle("Updated Project A");

        Project result = projectService.updateProject(project1.getId(), updatedProject);

        assertThat(result.getTitle()).isEqualTo("Updated Project A");
        verify(projectRepository, times(1)).findById(project1.getId());
        verify(projectRepository, times(1)).save(project1);
    }


    @Test
    public void testDeleteProject() {
        when(projectRepository.existsById(project1.getId())).thenReturn(true);

        boolean result = projectService.deleteProject(project1.getId());

        assertThat(result).isTrue();
        verify(projectRepository, times(1)).existsById(project1.getId());
        verify(projectRepository, times(1)).deleteById(project1.getId());
    }


}

