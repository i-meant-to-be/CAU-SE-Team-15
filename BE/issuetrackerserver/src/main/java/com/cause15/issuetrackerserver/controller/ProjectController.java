package com.cause15.issuetrackerserver.controller;


import com.cause15.issuetrackerserver.dto.ProjectCreateRequest;
import com.cause15.issuetrackerserver.model.Project;
import com.cause15.issuetrackerserver.service.ProjectService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Project Controller", description = "프로젝트 관련 API")
@RestController
@RequestMapping("/api")
public class ProjectController {
    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService=projectService;
    }


    @PostMapping(value = "/project")
    public ResponseEntity<Project> createProject(@RequestBody ProjectCreateRequest request) {
        Project project=new Project(request.getName());
        Project createdProject = projectService.createProject(project);
        return ResponseEntity.ok(createdProject);
    }
}
