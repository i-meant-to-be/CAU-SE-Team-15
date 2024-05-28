package com.cause15.issuetrackerserver.controller;


import com.cause15.issuetrackerserver.dto.CreateProjectRequest;
import com.cause15.issuetrackerserver.dto.CreateUserRequest;
import com.cause15.issuetrackerserver.dto.PatchProjectRequest;
import com.cause15.issuetrackerserver.model.Project;
import com.cause15.issuetrackerserver.model.User;
import com.cause15.issuetrackerserver.service.ProjectService;
import com.cause15.issuetrackerserver.service.UserService;
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
    private final UserService userService;

    public ProjectController(
            ProjectService projectService,
            UserService userService
    ) {
        this.projectService = projectService;
        this.userService = userService;
    }

    // APIs
    @Operation(
            summary = "새로운 프로젝트 생성",
            description = "새로운 프로젝트를 생성합니다."
    )
    @ApiResponse(responseCode = "200 OK", description = "성공적으로 새 프로젝트를 추가한 경우 반환")
    @RequestMapping(value = "/project", method = RequestMethod.POST)
    public ResponseEntity<Project> createProject(@RequestBody CreateProjectRequest createProjectRequest) {
        Project body = projectService.createProject(
                new Project(
                    createProjectRequest.getTitle(),
                    createProjectRequest.getDescription()
                )
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

    @Operation(
            summary = "특정 사용자가 포함된 프로젝트 검색",
            description = "특정 사용자가 포함된 프로젝트를 검색합니다."
    )
    @ApiResponse(responseCode = "200 OK", description = "검색 결과가 존재할 경우 반환")
    @RequestMapping(value = "/user/{id}/included_project", method = RequestMethod.GET)
    public ResponseEntity<Project> getProjectInWhichUserIsIncluded(
            @Parameter(description = "검색 대상 사용자의 UUID")
            @PathVariable(name = "id")
            UUID id
    ) {
        Optional<User> targetUser = userService.getUserById(id);

        if (targetUser.isPresent()) {
            List<Project> projectList = projectService.getAllProjects();

            for (Project project : projectList) {
                if (project.getUserIds().contains(id)) return ResponseEntity.ok(project);
            }

            return ResponseEntity.noContent().build();
        }
        else return ResponseEntity.notFound().build();
    }

    @Operation(
            summary = "프로젝트에 이슈 1건 추가",
            description = "이슈를 프로젝트에 추가합니다."
    )
    @ApiResponse(responseCode = "200 OK", description = "성공적으로 이슈를 추가했을 경우 반환")
    @RequestMapping(value = "/project/{project_id}/issue/{issue_id}", method = RequestMethod.POST)
    public ResponseEntity<Project> addIssueToProject(
            @Parameter(description = "이슈를 추가할 프로젝트의 UUID")
            @PathVariable(name = "project_id")
            UUID projectId,
            @Parameter(description = "추가할 이슈의 UUID")
            @PathVariable(name = "issue_id")
            UUID issueId
    ) {
        Optional<Project> targetProject = projectService.getProjectById(projectId);

        if (targetProject.isPresent()) {
            targetProject.get().getIssueIds().add(issueId);
            Project body = projectService.updateProject(projectId, targetProject.get());

            return body != null ?
                    ResponseEntity.ok(body) : ResponseEntity.internalServerError().build();
        }
        else return ResponseEntity.notFound().build();
    }

    @Operation(
            summary = "프로젝트에서 이슈 1건 삭제",
            description = "프로젝트에서 특정한 이슈 1건을 제외한 후, 그 이슈를 삭제합니다."
    )
    @ApiResponse(responseCode = "200 OK", description = "성공적으로 이슈를 삭제했을 경우 반환")
    @RequestMapping(value = "/project/{project_id}/issue/{issue_id}", method = RequestMethod.DELETE)
    public ResponseEntity<Boolean> deleteIssueFromProject(
            @Parameter(description = "이슈를 삭제할 프로젝트의 UUID")
            @PathVariable(name = "project_id")
            UUID projectId,
            @Parameter(description = "삭제할 이슈의 UUID")
            @PathVariable(name = "issue_id")
            UUID issueId
    ) {
        Optional<Project> targetProject = projectService.getProjectById(projectId);

        if (targetProject.isPresent()) {
            if (targetProject.get().getIssueIds().contains(issueId)) {
                targetProject.get().getIssueIds().remove(issueId);

                if (projectService.updateProject(projectId, targetProject.get()) != null) return ResponseEntity.ok(Boolean.TRUE);
                else return ResponseEntity.internalServerError().build();
            }
            else return ResponseEntity.notFound().build();
        }
        else return ResponseEntity.notFound().build();
    }

    @Operation(
            summary = "프로젝트에 사용자 1명 추가",
            description = "사용자를 프로젝트에 추가합니다."
    )
    @ApiResponse(responseCode = "200 OK", description = "성공적으로 사용자를 추가했을 경우 반환")
    @RequestMapping(value = "/project/{project_id}/issue/{user_id}", method = RequestMethod.POST)
    public ResponseEntity<Project> addUserToProject(
            @Parameter(description = "사용자를 추가할 프로젝트의 UUID")
            @PathVariable(name = "project_id")
            UUID projectId,
            @Parameter(description = "추가할 이슈의 UUID")
            @PathVariable(name = "user_id")
            UUID userId
    ) {
        Optional<Project> targetProject = projectService.getProjectById(projectId);

        if (targetProject.isPresent()) {
            targetProject.get().getUserIds().add(userId);
            Project body = projectService.updateProject(projectId, targetProject.get());

            return body != null ?
                    ResponseEntity.ok(body) : ResponseEntity.internalServerError().build();
        }
        else return ResponseEntity.notFound().build();
    }

    @Operation(
            summary = "프로젝트에서 사용자 1명 제외",
            description = "프로젝트에서 특정한 사용자 1명을 제외합니다. 사용자는 삭제되지 않습니다."
    )
    @ApiResponse(responseCode = "200 OK", description = "성공적으로 사용자를 삭제했을 경우 반환")
    @RequestMapping(value = "/project/{project_id}/user/{user_id}", method = RequestMethod.DELETE)
    public ResponseEntity<Boolean> excludeUserFromProject(
            @Parameter(description = "이슈를 삭제할 프로젝트의 UUID")
            @PathVariable(name = "project_id")
            UUID projectId,
            @Parameter(description = "삭제할 이슈의 UUID")
            @PathVariable(name = "user_id")
            UUID userId
    ) {
        Optional<Project> targetProject = projectService.getProjectById(projectId);

        if (targetProject.isPresent()) {
            if (targetProject.get().getUserIds().contains(userId)) {
                targetProject.get().getUserIds().remove(userId);

                if (projectService.updateProject(projectId, targetProject.get()) != null) return ResponseEntity.ok(Boolean.TRUE);
                else return ResponseEntity.internalServerError().build();
            }
            else return ResponseEntity.notFound().build();
        }
        else return ResponseEntity.notFound().build();
    }
}
