package com.cause15.issuetrackerserver.controller;

import com.cause15.issuetrackerserver.dto.CreateIssueRequest;
import com.cause15.issuetrackerserver.dto.PatchIssueRequest;
import com.cause15.issuetrackerserver.model.*;
import com.cause15.issuetrackerserver.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@Tag(name = "Issue Controller", description = "이슈 관련 API")
@RestController
@RequestMapping("/api")
public class IssueController {
    // Dependency injection
    private final IssueService issueService;
    private final UserService userService;
    private final OktService oktService;
    private final ProjectService projectService;
    private final CommentService commentService;

    public IssueController(
            IssueService issueService,
            UserService userService,
            OktService oktService,
            ProjectService projectService,
            CommentService commentService
    ) {
        this.issueService = issueService;
        this.userService = userService;
        this.oktService= oktService;
        this.projectService = projectService;
        this.commentService = commentService;
    }

    // APIs
    @Operation(
            summary = "프로젝트에 새 이슈 생성",
            description = "특정 프로젝트에 새로운 이슈를 생성 및 추가합니다."
    )
    @ApiResponse(responseCode = "200 OK", description = "성공적으로 새 이슈를 추가한 경우 반환")
    @RequestMapping(value = "/project/{id}/issue", method = RequestMethod.POST)
    public ResponseEntity<Issue> createIssue(
            @RequestBody CreateIssueRequest createIssueRequest,
            @Parameter(description = "이슈를 추가할 프로젝트의 ID")
            @PathVariable(name = "id")
            UUID projectId
    ) {
        Optional<Project> targetProject = projectService.getProjectById(projectId);

        if (targetProject.isPresent()) {
            // 새로운 Issue가 생성되면 Title, Description에서 토큰화한 테그들을 저장합니다.
            List<String> newTagsFromTitle = oktService.ExtractKoreanTokens(createIssueRequest.getTitle());
            List<String> newTagsFromDescription = oktService.ExtractKoreanTokens(createIssueRequest.getDescription());
            List<String> Tags = Stream
                    .concat(newTagsFromTitle.stream(), newTagsFromDescription.stream())
                    .distinct()
                    .toList();

            Issue newIssue = issueService.createIssue(
                    new Issue(
                            createIssueRequest.getTitle(),
                            createIssueRequest.getDescription(),
                            createIssueRequest.getType(),
                            createIssueRequest.getReporterId(),
                            Tags
                    )
            );
            targetProject.get().getIssueIds().add(newIssue.getId());

            if (projectService.updateProject(projectId, targetProject.get()) != null) return ResponseEntity.ok(newIssue);
            else return ResponseEntity.internalServerError().build();
        }
        else return ResponseEntity.notFound().build();
    }

    @Operation(
            summary = "이슈 1건 삭제",
            description = "특정 이슈를 DB에서 삭제합니다. 삭제할 이슈가 프로젝트에 포함되어 있을 경우, 그 프로젝트에서 이슈의 UUID도 함께 삭제합니다."
    )
    @ApiResponse(responseCode = "200 OK", description = "성공적으로 이슈를 삭제했을 경우 반환")
    @RequestMapping(value = "/issue/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Boolean> deleteIssue(
            @Parameter(description = "삭제할 이슈의 UUID")
            @PathVariable(name = "id")
            UUID id
    ) {
        Optional<Issue> targetIssue = issueService.getIssueById(id);

        if (targetIssue.isPresent()) {
            if (issueService.deleteIssue(id)) {
                targetIssue.get().getCommentIds().forEach(commentService::deleteComment);

                List<Project> projectList = projectService.getAllProjects();
                projectList.forEach((Project project) -> {
                    if (project.getIssueIds().contains(id)) {
                        project.getIssueIds().remove(id);
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
            summary = "이슈 1건의 데이터 조회",
            description = "특정한 이슈의 데이터를 반환합니다."
    )
    @ApiResponse(responseCode = "200 OK", description = "주어진 ID에 대응하는 이슈가 있을 경우 반환")
    @RequestMapping(value = "/issue/{id}", method = RequestMethod.GET)
    public ResponseEntity<Issue> getIssue(
            @Parameter(description = "조회할 이슈의 UUID")
            @PathVariable(name = "id")
            UUID id
    ) {
        Optional<Issue> body = issueService.getIssueById(id);
        return body.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "이슈 1건의 데이터 수정",
            description = "특정한 이슈의 데이터를 수정합니다."
    )
    @ApiResponse(responseCode = "200 OK", description = "성공적으로 이슈를 수정했을 경우 반환")
    @RequestMapping(value = "/issue/{id}", method = RequestMethod.PATCH)
    public ResponseEntity<Issue> patchIssue(
            @RequestBody PatchIssueRequest patchIssueRequest,
            @Parameter(description = "수정할 이슈의 UUID")
            @PathVariable(name = "id")
            UUID id
    ) {
        Optional<Issue> targetIssue = issueService.getIssueById(id);

        if (targetIssue.isPresent()) {
            if (patchIssueRequest.getTitle() != null) targetIssue.get().setTitle(patchIssueRequest.getTitle());
            if (patchIssueRequest.getDescription() != null) targetIssue.get().setDescription(patchIssueRequest.getDescription());
            if (patchIssueRequest.getType() != null) targetIssue.get().setType(patchIssueRequest.getType());
            if (patchIssueRequest.getReporterId() != null) targetIssue.get().setReporterId(patchIssueRequest.getReporterId());
            if (patchIssueRequest.getFixerId() != null) targetIssue.get().setFixerId(patchIssueRequest.getFixerId());
            if (patchIssueRequest.getState() != null) targetIssue.get().setState(patchIssueRequest.getState());
            if (patchIssueRequest.getAssigneeId() != null) targetIssue.get().setAssigneeId(patchIssueRequest.getAssigneeId());

            // 만약 이번 패치로 issueState가 RESOLVED 되면, 해당 fixer의 tag필드를 수정합니다.
            if (patchIssueRequest.getState() == IssueState.RESOLVED){
                Optional<User> dev = userService.getUserById(targetIssue.get().getFixerId());

                if (dev.isPresent()) {
                    dev.get().updateTag(targetIssue.get().getTags());
                    userService.updateUser(dev.get().getId(), dev.get());
                }
                else return ResponseEntity.internalServerError().build();
            }

            if (issueService.updateIssue(id, targetIssue.get()) != null) return ResponseEntity.ok(targetIssue.get());
            else return ResponseEntity.internalServerError().build();
        }
        else return ResponseEntity.notFound().build();
    }


    @Operation(
            summary = "이슈 데이터 조회 및 검색",
            description = """
                    '이름(title)' 또는 '상태(state)'를 키워드로 하여, 이슈를 검색합니다.
                    매개변수가 비어 있을 경우, 전체 이슈를 반환합니다."""
    )
    @ApiResponse(responseCode = "200 OK", description = "이슈 검색 결과가 있을 경우 경우 반환")
    @RequestMapping(value = "/issue", method = RequestMethod.GET)
    public ResponseEntity<List<Issue>> findIssueByTitleOrState(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) IssueState state
    ) {
        if (title == null && state == null) {
            List<Issue> body = issueService.getAllIssues();
            return body != null && !body.isEmpty() ?
                    ResponseEntity.ok(body) : ResponseEntity.noContent().build();
        }
        else if (title != null && state != null) {
            List<Issue> body = issueService.getIssueByTitleAndState(title, state);
            return body != null && !body.isEmpty() ?
                    ResponseEntity.ok(body) : ResponseEntity.noContent().build();
        }
        else {
            List<Issue> body = (title != null) ? issueService.getIssueByTitle(title) : issueService.getIssueByState(state);
            return body != null && !body.isEmpty() ?
                    ResponseEntity.ok(body) : ResponseEntity.noContent().build();
        }
    }

    @Operation(
            summary = "특정 사용자와 관련된 이슈 데이터 검색",
            description = """
                    '사용자 ID'를 키워드로 하여, 이슈를 검색합니다.
                    Tester인 경우, reporter 필드가 해당 Tester의 UUID인 이슈를 반환합니다.
                    Developer인 경우, fixer 또는 assignee 필드가 해당 Developer인 이슈를 반환합니다.
                    그 외의 경우, 아무 이슈도 반환하지 않습니다."""
    )
    @ApiResponse(responseCode = "200 OK", description = "주어진 사용자 ID와 관련된 이슈가 있을 경우 반환")
    @RequestMapping(value = "/user/{id}/issue", method = RequestMethod.GET)
    public ResponseEntity<List<Issue>> findIssueByUserId(
            @Parameter(description = "이슈를 검색할 사용자의 UUID")
            @PathVariable(name = "id")
            UUID userId
    ) {
        Optional<User> targetUser = userService.getUserById(userId);

        if (targetUser.isPresent()) {
            if (targetUser.get().getType() == UserType.TESTER) {
                List<Issue> body = issueService.getIssueForTester(targetUser.get().getId());
                return !body.isEmpty() ?
                        ResponseEntity.ok(body) : ResponseEntity.noContent().build();
            }
            else if (targetUser.get().getType() == UserType.DEVELOPER) {
                List<Issue> body = issueService.getIssueForDeveloper(targetUser.get().getId());
                return !body.isEmpty() ?
                        ResponseEntity.ok(body) : ResponseEntity.noContent().build();
            }
        }

        return ResponseEntity.notFound().build();
    }

    // This is deprecated API
    /*
    @Operation(
            summary = "이슈 1건 삭제",
            description = "특정 이슈를 DB에서 삭제합니다."
    )
    @ApiResponse(responseCode = "200 OK", description = "성공적으로 이슈를 삭제했을 경우 반환")
    @RequestMapping(value = "/issue/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Boolean> deleteIssue(
            @Parameter(description = "삭제할 이슈의 UUID")
            @PathVariable(name = "id")
            UUID id
    ) {
        Optional<Issue> targetIssue = issueService.getIssueById(id);

        if (targetIssue.isPresent()) {
            return issueService.deleteIssue(id) ?
                ResponseEntity.ok(Boolean.TRUE) : ResponseEntity.internalServerError().build();
        }
        else return ResponseEntity.notFound().build();
    }
     */

    @Operation(
            summary = "이슈를 기준으로 추천 Developer 검색",
            description = "'이슈 ID'를 키워드로 하여, 적절한 Developer를 검색합니다."
    )
    @RequestMapping(value = "/issue/{id}/recommended_dev", method = RequestMethod.GET)
    public ResponseEntity<List<User>> findRecommendedDevByIssueId (
            @Parameter(description = "issue UUID")
            @PathVariable(name = "id")
            UUID id
    ) {
        Optional<Issue> issue = issueService.getIssueById(id);

        if (issue.isPresent()) {
            List<User> body = userService.getAllUserByType(UserType.DEVELOPER);

            if (body != null) {
                body.sort((targetDev1, targetDev2) -> {
                    float jac1 = targetDev1.calculateJaccard(issue);
                    float jac2 = targetDev2.calculateJaccard(issue);

                    return Float.compare(jac2, jac1);
                });

                return ResponseEntity.ok(body);
            }
            else return ResponseEntity.noContent().build();
        }
        else return ResponseEntity.notFound().build();
    }

    @Operation(
            summary = "Developer를 기준으로 추천 이슈 검색",
            description = "'Developer ID'를 키워드로 하여, 이슈를 검색합니다."
    )
    @RequestMapping(value = "/user/{id}/recommended_issue", method = RequestMethod.GET)
    public ResponseEntity<List<Issue>> findRecommendedIssueById(
            @Parameter(description = "추천 이슈를 검색할 개발자의 UUID")
            @PathVariable(name = "id")
            UUID id
    ) {
        Optional<User> targetDev = userService.getUserById(id);

        if (targetDev.isPresent()) {
            if (targetDev.get().getType() == UserType.DEVELOPER) {
                List<Issue> body = issueService.getIssueByState(IssueState.NEW);

                if (!body.isEmpty()) {
                    body.sort((issue1, issue2) -> {
                        float jac1 = targetDev.get().calculateJaccard(Optional.ofNullable(issue1));
                        float jac2 = targetDev.get().calculateJaccard(Optional.ofNullable(issue2));

                        return Float.compare(jac2, jac1);
                    });

                    return ResponseEntity.ok(body);
                }
                else return ResponseEntity.noContent().build();
            }
            else return ResponseEntity.badRequest().build();
        }
        else return ResponseEntity.notFound().build();
    }

    @Operation(
            summary = "특정 프로젝트에 포함된 모든 이슈 조회",
            description = "특정 프로젝트 1건에 포함된 모든 이슈의 데이터를 조회합니다."
    )
    @RequestMapping(value = "/project/{id}/issue", method = RequestMethod.GET)
    public ResponseEntity<List<Issue>> getAllIssueIncludedInProject(
            @Parameter(description = "이슈를 조회할 프로젝트의 UUID")
            @PathVariable(name = "id")
            UUID id
    ) {
        Optional<Project> targetProject = projectService.getProjectById(id);

        if (targetProject.isPresent()) {
            List<UUID> issueIds = targetProject.get().getIssueIds();

            if (!issueIds.isEmpty()) {
                List<Issue> body = new ArrayList<>();

                issueIds.forEach((UUID issueId) -> {
                    Optional<Issue> targetIssue = issueService.getIssueById(issueId);
                    targetIssue.ifPresent(body::add);
                });

                return ResponseEntity.ok(body);
            }
            else return ResponseEntity.noContent().build();
        }
        else return ResponseEntity.notFound().build();
    }
}
