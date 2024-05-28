package com.cause15.issuetrackerserver.controller;


import com.cause15.issuetrackerserver.dto.CreateProjectRequest;
import com.cause15.issuetrackerserver.dto.CreateUserRequest;
import com.cause15.issuetrackerserver.dto.PatchProjectRequest;
import com.cause15.issuetrackerserver.model.Project;
import com.cause15.issuetrackerserver.model.User;
import com.cause15.issuetrackerserver.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Tag(name = "Project Controller", description = "프로젝트 관련 API")
@RestController
@RequestMapping("/api")
public class ProjectController {
    // Dependency injection
    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService=projectService;
    }

    // APIs
    @Operation(
            summary = "새로운 프로젝트 추가",
            description = "새로운 프로젝트를 DB에 추가합니다."
    )
    @ApiResponse(responseCode = "200 OK", description = "성공적으로 새 프로젝트를 추가한 경우 반환")
    @RequestMapping(value = "/project", method = RequestMethod.POST)
    public ResponseEntity<Project> createProject(@RequestBody CreateProjectRequest createProjectRequest) {
        Project body = new Project(
                createProjectRequest.getTitle(),
                createProjectRequest.getDescription()
        );
        return ResponseEntity.ok(body);
    }

    @Operation(
            summary = "프로젝트 1건의 데이터 조회",
            description = "특정한 프로젝트의 데이터를 반환합니다."
    )
    @ApiResponse(responseCode = "200 OK", description = "주어진 ID에 대응하는 프로젝트가 존재할 경우 반환")
    @RequestMapping(value = "/project/{id}", method = RequestMethod.GET)
    public ResponseEntity<Project> getProject(
            @Parameter(description = "조회할 프로젝트의 UUID")
            @PathVariable(name = "id")
            UUID id
    ) {
        Optional<Project> body = projectService.getProjectById(id);
        return body.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "프로젝트 전체의 데이터 조회",
            description = "전체 프로젝트의 데이터를 반환합니다."
    )
    @ApiResponse(responseCode = "200 OK", description = "프로젝트가 1건 이상 존재할 시 반환")
    @RequestMapping(value = "/project", method = RequestMethod.GET)
    public ResponseEntity<List<Project>> getAllProjects() {
        List<Project> body = projectService.getAllProjects();

        if (body != null && !body.isEmpty()) return ResponseEntity.ok(body);
        else return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "프로젝트 1건 삭제",
            description = "특정 프로젝트를 DB에서 삭제합니다."
    )
    @ApiResponse(responseCode = "200 OK", description = "성공적으로 프로젝트를 삭제했을 경우 반환")
    @RequestMapping(value = "/project/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Boolean> deleteProject(
            @Parameter(description = "삭제할 프로젝트의 UUID")
            @PathVariable(name = "id")
            UUID id
    ) {
        Optional<Project> targetProject = projectService.getProjectById(id);

        if (targetProject.isPresent()) {
            return projectService.deleteProject(id) ?
                    ResponseEntity.ok(Boolean.TRUE) : ResponseEntity.internalServerError().build();
        }
        else return ResponseEntity.notFound().build();
    }

    @Operation(
            summary = "프로젝트 1건의 데이터 수정",
            description = "특정 프로젝트의 데이터를 수정합니다."
    )
    @ApiResponse(responseCode = "200 OK", description = "성공적으로 프로젝트를 수정했을 경우 반환")
    @RequestMapping(value = "/project/{id}", method = RequestMethod.PATCH)
    public ResponseEntity<Project> patchProject(
            @RequestBody PatchProjectRequest patchProjectRequest,
            @Parameter(description = "수정할 프로젝트의 UUID")
            @PathVariable(name = "id")
            UUID id
    ) {
        Optional<Project> targetProject = projectService.getProjectById(id);
        Project body;

        if (targetProject.isPresent()) {
            if (patchProjectRequest.getDescription() != null)  targetProject.get().setDescription(patchProjectRequest.getDescription());
            if (patchProjectRequest.getTitle() != null)  targetProject.get().setTitle(patchProjectRequest.getTitle());
            body = projectService.updateProject(id, targetProject.get());

            return body != null ? ResponseEntity.ok(body) : ResponseEntity.internalServerError().build();

        }
        else return ResponseEntity.notFound().build();
    }

    // TODO: 특정 사용자가 포함된 프로젝트 조회
}
