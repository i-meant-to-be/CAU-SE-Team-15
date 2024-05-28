package com.cause15.issuetrackerserver.controller;

import com.cause15.issuetrackerserver.dto.CreateUserRequest;
import com.cause15.issuetrackerserver.dto.LoginRequest;
import com.cause15.issuetrackerserver.dto.PatchUserRequest;
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

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Tag(name = "User Controller", description = "사용자 관련 API")
@RestController
@RequestMapping("/api")
public class UserController {
    // Dependency injection
    private final UserService userService;
    private final ProjectService projectService;

    public UserController(
            UserService userService,
            ProjectService projectService
    ) {
        this.projectService = projectService;
        this.userService = userService;
    }

    // APIs
    @Operation(
            summary = "새로운 사용자 생성",
            description = "새로운 사용자를 생성합니다."
    )
    @ApiResponse(responseCode = "200 OK", description = "성공적으로 새 사용자를 추가한 경우 반환")
    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public ResponseEntity<User> createUser(@RequestBody CreateUserRequest createUserRequest) {
        User body = userService.createUser(
                new User(
                        createUserRequest.getName(),
                        createUserRequest.getPassword(),
                        createUserRequest.getType()
                )
        );
        return ResponseEntity.ok(body);
    }

    @Operation(
            summary = "사용자 1명의 데이터 조회",
            description = "특정 사용자의 데이터를 반환합니다."
    )
    @ApiResponse(responseCode = "200 OK", description = "주어진 ID에 대응하는 사용자가 있을 경우 반환")
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public ResponseEntity<User> getUser(
            @Parameter(description = "조회할 사용자의 UUID")
            @PathVariable(name = "id")
            UUID id
    ) {
        Optional<User> body = userService.getUserById(id);
        return body.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "사용자 1명의 데이터 수정",
            description = "특정 사용자의 데이터를 수정합니다."
    )
    @ApiResponse(responseCode = "200 OK", description = "성공적으로 이슈를 수정했을 경우 반환")
    @RequestMapping(value = "/user/{id}", method = RequestMethod.PATCH)
    public ResponseEntity<User> patchUser(
            @RequestBody PatchUserRequest patchUserRequest,
            @Parameter(description = "수정할 사용자의 UUID")
            @PathVariable(name = "id")
            UUID id
    ) {
        Optional<User> targetUser = userService.getUserById(id);

        if (targetUser.isPresent()) {
            if (patchUserRequest.getPassword() != null) targetUser.get().setPassword(patchUserRequest.getPassword());
            if (patchUserRequest.getUsername() != null) targetUser.get().setName(patchUserRequest.getUsername());

            if (userService.updateUser(id, targetUser.get()) != null) return ResponseEntity.ok(targetUser.get());
            else return ResponseEntity.internalServerError().build();
        }
        else return ResponseEntity.notFound().build();
    }

    @Operation(
            summary = "사용자 전체의 데이터 조회",
            description = "전체 사용자의 데이터를 반환합니다."
    )
    @ApiResponse(responseCode = "200 OK", description = "사용자가 1명 이상 있을 시 반환")
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> body = userService.getAllUsers();

        if (body != null && !body.isEmpty()) return ResponseEntity.ok(body);
        else return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "사용자 1명 삭제",
            description = "특정 사용자를 DB에서 삭제합니다."
    )
    @ApiResponse(responseCode = "200 OK", description = "성공적으로 사용자를 삭제했을 경우 반환")
    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Boolean> deleteUser(
            @Parameter(description = "삭제할 사용자의 UUID")
            @PathVariable(name = "id")
            UUID id
    ) {
        Optional<User> targetUser = userService.getUserById(id);

        if (targetUser.isPresent()) {
            if (userService.deleteUser(id)) {
                List<Project> projectList = projectService.getAllProjects();
                projectList.forEach((Project project) -> {
                    if (project.getUserIds().contains(id)) {
                        project.getUserIds().remove(id);
                        projectService.updateProject(project.getId(), project);
                    }
                });

                return ResponseEntity.ok(Boolean.TRUE);
            }
            else return ResponseEntity.internalServerError().build();
        }
        else return ResponseEntity.notFound().build();
    }

    @Operation(
            summary = "로그인",
            description = "사용자 이름과 비밀번호로 서비스에 로그인합니다."
    )
    @ApiResponse(responseCode = "200 OK", description = "성공적으로 로그인한 경우 반환")
    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    public ResponseEntity<User> login(@RequestBody LoginRequest loginRequest) {
        User targetUser = userService.getUserByName(loginRequest.getUsername());

        if (targetUser != null) {
            if (loginRequest.getPassword().equals(targetUser.getPassword())) {
                return ResponseEntity.ok(targetUser);
            }
            else return ResponseEntity.badRequest().build();
        }
        else return ResponseEntity.notFound().build();
    }

    @Operation(
            summary = "프로젝트에 사용자 1명 포함",
            description = "사용자를 프로젝트에 포함합니다. 이 API를 사용하기 위해서는, 사용자를 미리 생성해야 합니다."
    )
    @ApiResponse(responseCode = "200 OK", description = "성공적으로 사용자를 추가했을 경우 반환")
    @RequestMapping(value = "/project/{project_id}/issue/{user_id}", method = RequestMethod.POST)
    public ResponseEntity<Project> addUserToProject(
            @Parameter(description = "사용자를 추가할 프로젝트의 UUID")
            @PathVariable(name = "project_id")
            UUID projectId,
            @Parameter(description = "추가할 사용자의 UUID")
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
            @Parameter(description = "사용자를 제외할 프로젝트의 UUID")
            @PathVariable(name = "project_id")
            UUID projectId,
            @Parameter(description = "제외할 사용자의 UUID")
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
