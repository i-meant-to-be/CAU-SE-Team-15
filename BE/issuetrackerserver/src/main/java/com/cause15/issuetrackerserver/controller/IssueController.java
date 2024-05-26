package com.cause15.issuetrackerserver.controller;

import com.cause15.issuetrackerserver.dto.CreateIssueRequest;
import com.cause15.issuetrackerserver.dto.PatchIssueRequest;
import com.cause15.issuetrackerserver.model.Issue;
import com.cause15.issuetrackerserver.model.IssueState;
import com.cause15.issuetrackerserver.model.User;
import com.cause15.issuetrackerserver.model.UserType;
import com.cause15.issuetrackerserver.service.IssueService;
import com.cause15.issuetrackerserver.service.OktService;
import com.cause15.issuetrackerserver.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    public IssueController(IssueService issueService, UserService userService,OktService oktService) {
        this.issueService = issueService;
        this.userService = userService;
        this.oktService= oktService;
    }

    // APIs
    @Operation(
            summary = "새로운 이슈 추가",
            description = "새로운 이슈를 DB에 추가합니다."
    )
    @ApiResponse(responseCode = "200 OK", description = "성공적으로 새 이슈를 추가한 경우 반환")
    @RequestMapping(value = "/issue", method = RequestMethod.POST)
    public ResponseEntity<Issue> createIssue(@RequestBody CreateIssueRequest createIssueRequest) {

        //새로운 Issue가 생성되면 Title,Description에서 토큰화한 테그들을 저장합니다.
        List<String>newTagsfromTitle=oktService.ExtractKoreanTokens(createIssueRequest.getTitle());
        List<String>newTagsfromDescription=oktService.ExtractKoreanTokens(createIssueRequest.getDescription());
        List<String> Tags = Stream.concat(newTagsfromTitle.stream(), newTagsfromDescription.stream()).distinct()
                .toList();
        Issue body = issueService.createIssue(
                new Issue(
                        createIssueRequest.getTitle(),
                        createIssueRequest.getDescription(),
                        createIssueRequest.getType(),
                        createIssueRequest.getReporterId(),
                        Tags
                )
        );
        return ResponseEntity.ok(body);
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
        Issue body = issueService.getIssueById(id);

        if (body != null) return ResponseEntity.ok(body);
        else return ResponseEntity.notFound().build();
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
        Issue targetIssue = issueService.getIssueById(id);
        Issue body;

        if (targetIssue != null) {
            if (patchIssueRequest.getTitle() != null) targetIssue.setTitle(patchIssueRequest.getTitle());
            if (patchIssueRequest.getDescription() != null) targetIssue.setDescription(patchIssueRequest.getDescription());
            if (patchIssueRequest.getType() != null) targetIssue.setType(patchIssueRequest.getType());
            if (patchIssueRequest.getReporterId() != null) targetIssue.setReporterId(patchIssueRequest.getReporterId());
            if (patchIssueRequest.getFixerId() != null) targetIssue.setFixerId(patchIssueRequest.getFixerId());
            if (patchIssueRequest.getState() != null){
                targetIssue.setState(patchIssueRequest.getState());
            }
            if (patchIssueRequest.getAssigneeId() != null) targetIssue.setAssigneeId(patchIssueRequest.getAssigneeId());
            body = issueService.updateIssue(id, targetIssue);

            //만약 이번 패치로 issueState가 RESOLVED 되면, 해당 fixer의 tag필드를 수정합니다.
            if(patchIssueRequest.getState()==IssueState.RESOLVED){
                User dev=userService.getUserById(targetIssue.getFixerId());
                dev.updateTag(targetIssue.getTags());
                userService.updateUser(targetIssue.getFixerId(),dev);
            }
            return body != null ? ResponseEntity.ok(body) : ResponseEntity.internalServerError().build();
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
        User targetUser = userService.getUserById(userId);

        if (targetUser != null) {
            if (targetUser.getType() == UserType.TESTER) {
                List<Issue> body = issueService.getIssueForTester(targetUser.getId());
                return !body.isEmpty() ?
                        ResponseEntity.ok(body) : ResponseEntity.noContent().build();
            }
            else if (targetUser.getType() == UserType.DEVELOPER) {
                List<Issue> body = issueService.getIssueForDeveloper(targetUser.getId());
                return !body.isEmpty() ?
                        ResponseEntity.ok(body) : ResponseEntity.noContent().build();
            }
        }

        return ResponseEntity.notFound().build();
    }

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
        Issue targetIssue = issueService.getIssueById(id);

        if (targetIssue != null) {
            return issueService.deleteIssue(id) ?
                ResponseEntity.ok(Boolean.TRUE) : ResponseEntity.internalServerError().build();
        }
        else return ResponseEntity.notFound().build();
    }

    @Operation(
            summary = "Developer의 추천 issue 검색",
            description = """
                    'Developer ID'를 키워드로 하여, 이슈를 검색합니다. """
    )
    @GetMapping(value="/Jaccard/{id}")
    public ResponseEntity<List<Issue>>findRecommendedIssueById(
            @Parameter(description = "Developer UUID")
            @PathVariable(name="id")
            UUID userId
    ){
        User developer = userService.getUserById(userId);
        if (developer.getType() != UserType.DEVELOPER) {
            return ResponseEntity.badRequest().build();
        }
        //List<Issue> body = issueService.getAllIssues();
        List<Issue> body = issueService.getIssueByState(IssueState.NEW);
        if(body.isEmpty())
            return ResponseEntity.noContent().build();
        body.sort((issue1,issue2)->{
            float jac1 = developer.calculateJaccard(issue1);
            float jac2 = developer.calculateJaccard(issue2);
            return Float.compare(jac2, jac1);
        });
        return ResponseEntity.ok(body);
    }
}
