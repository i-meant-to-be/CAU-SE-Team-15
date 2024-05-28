package com.cause15.issuetrackerserver.service;

import com.cause15.issuetrackerserver.model.Project;
import com.cause15.issuetrackerserver.repository.ProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class ProjectServiceTest {
    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private ProjectService projectService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllProjects(){
        Project project = new Project("test", "asd");
        projectService.getAllProjects();
    }


}
