package com.cause15.issuetrackerserver.controller;

import com.cause15.issuetrackerserver.dto.CreateCommentRequest;
import com.cause15.issuetrackerserver.dto.PatchCommentRequest;
import com.cause15.issuetrackerserver.dto.PatchIssueRequest;
import com.cause15.issuetrackerserver.model.Comment;
import com.cause15.issuetrackerserver.model.Issue;
import com.cause15.issuetrackerserver.model.User;
import com.cause15.issuetrackerserver.model.UserType;
import com.cause15.issuetrackerserver.service.CommentService;
import com.cause15.issuetrackerserver.service.IssueService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Tag(name = "Comment Controller", description = "댓글 관련 API")
@RestController
@RequestMapping("/api")
public class CommentController {
    // Dependency injection
    private final CommentService commentService;
    private final IssueService issueService;

    public CommentController(CommentService commentService, IssueService issueService) {
        this.commentService = commentService;
        this.issueService = issueService;
    }

    // APIs
    @Operation(
            summary = "이슈에 새 댓글 생성",
            description = "특정 이슈에 새로운 댓글을 생성 및 추가합니다."
    )
    @ApiResponse(responseCode = "200 OK", description = "성공적으로 새 댓글을 추가한 경우 반환")
    @RequestMapping(value = "/issue/{id}/comment", method = RequestMethod.POST)
    public ResponseEntity<Comment> createComment(
            @RequestBody CreateCommentRequest createCommentRequest,
            @Parameter(description = "댓글을 추가할 이슈의 ID")
            @PathVariable(name = "id")
            UUID issueId
    ) {
        Optional<Issue> targetIssue = issueService.getIssueById(issueId);

        if (targetIssue.isPresent()) {
            Comment newComment = commentService.createComment(
                    new Comment(
                            createCommentRequest.getBody(),
                            createCommentRequest.getAuthorId()
                    )
            );
            targetIssue.get().getCommentIds().add(newComment.getId());

            if (issueService.updateIssue(issueId, targetIssue.get()) != null) return ResponseEntity.ok(newComment);
            else return ResponseEntity.internalServerError().build();
        }
        else return ResponseEntity.notFound().build();
    }

    @Operation(
            summary = "전체 댓글 조회",
            description = "전체 댓글의 데이터를 반환합니다."
    )
    @ApiResponse(responseCode = "200 OK", description = "댓글 검색 결과가 있을 경우 반환")
    @RequestMapping(value = "/comment", method = RequestMethod.GET)
    public ResponseEntity<List<Comment>> getAllComments() {
        List<Comment> body = commentService.getAllComments();
        return !body.isEmpty() ?
                ResponseEntity.ok(body) : ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "특정 댓글 조회",
            description = "특정 댓글의 데이터를 반환합니다."
    )
    @ApiResponse(responseCode = "200 OK", description = "주어진 ID에 대응하는 댓글이 있을 경우 반환")
    @RequestMapping(value = "/comment/{id}", method = RequestMethod.GET)
    public ResponseEntity<Comment> getComment(
            @Parameter(description = "조회할 댓글의 UUID")
            @PathVariable(name = "id")
            UUID id
    ) {
        Optional<Comment> body = commentService.getCommentById(id);
        return body.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @Operation(
            summary = "댓글 1개 삭제",
            description = "특정 댓글을 DB에서 삭제합니다. 삭제할 댓글이 이슈에 포함되어 있을 경우, 그 이슈에서 댓글의 UUID도 함께 삭제합니다."
    )
    @ApiResponse(responseCode = "200 OK", description = "성공적으로 댓글을 삭제한 경우 반환")
    @RequestMapping(value = "/comment/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Boolean> deleteComment(
            @Parameter(description = "삭제할 댓글의 UUID")
            @PathVariable(name = "id")
            UUID id
    ) {
        Optional<Comment> targetComment = commentService.getCommentById(id);


        if (targetComment.isPresent()) {
            if (commentService.deleteComment(id)) {
                List<Issue> issueList = issueService.getAllIssues();
                issueList.forEach((Issue issue) -> {
                    if (issue.getCommentIds().contains(id)) {
                        issue.getCommentIds().remove(id);
                        issueService.updateIssue(issue.getId(), issue);
                    }
                });

                return ResponseEntity.ok(Boolean.TRUE);
            }
            else return ResponseEntity.internalServerError().build();
        }
        else return ResponseEntity.notFound().build();
    }

    @Operation(
            summary = "특정 이슈에 달린 댓글 데이터 검색",
            description = "'이슈 ID'를 키워드로 하여, 댓글을 검색합니다."
    )
    @ApiResponse(responseCode = "200 OK", description = "주어진 이슈 ID에 달린 댓글이 있을 경우 반환")
    @RequestMapping(value = "/issue/{id}/comment", method = RequestMethod.GET)
    public ResponseEntity<List<Comment>> getCommentByIssueId(@PathVariable(name = "id") UUID issueId) {
        Optional<Issue> targetIssue = issueService.getIssueById(issueId);
        List<Comment> body = new ArrayList<>();

        if (targetIssue.isPresent()) {
            if (!targetIssue.get().getCommentIds().isEmpty()) {
                targetIssue.get().getCommentIds().forEach(commentId -> {
                    Optional<Comment> targetComment = commentService.getCommentById(commentId);
                    targetComment.ifPresent(body::add);
                });
                return ResponseEntity.ok(body);
            }
            else return ResponseEntity.noContent().build();
        }
        else return ResponseEntity.notFound().build();
    }

    @Operation(
            summary = "댓글 1개의 데이터 수정",
            description = "특정한 댓글의 데이터를 수정합니다."
    )
    @ApiResponse(responseCode = "200 OK", description = "성공적으로 이슈를 수정했을 경우 반환")
    @RequestMapping(value = "/comment/{id}", method = RequestMethod.PATCH)
    public ResponseEntity<Comment> patchComment(
            @RequestBody PatchCommentRequest patchCommentRequest,
            @PathVariable(name = "id") UUID id
    ) {
        Optional<Comment> targetComment = commentService.getCommentById(id);

        if (targetComment.isPresent()) {
            if (patchCommentRequest.getBody() != null) targetComment.get().setBody(patchCommentRequest.getBody());

            return commentService.updateComment(id, targetComment.get()) != null ?
                    ResponseEntity.ok(targetComment.get()) : ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.notFound().build();
    }
}