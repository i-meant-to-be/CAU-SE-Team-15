package com.cause15.issuetrackerserver.controller;

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
            summary = "전체 댓글 조회",
            description = "전체 댓글의 데이터를 반환합니다."
    )
    @ApiResponse(responseCode = "200 OK", description = "댓글 검색 결과가 있을 경우 반환")
    @RequestMapping(value = "/comment", method = RequestMethod.GET)
    public ResponseEntity<List<Comment>> getAllComments() {
        List<Comment> body = commentService.getAllComments();
        return !body.isEmpty() ?
                ResponseEntity.noContent().build() : ResponseEntity.ok(body);
    }

    @Operation(
            summary = "특정 댓글 조회",
            description = "특정 댓글의 데이터를 반환합니다."
    )
    @ApiResponse(responseCode = "200 OK", description = "주어진 ID에 대응하는 댓글이 있을 경우 반환")
    @RequestMapping(value = "/comment/{id}", method = RequestMethod.GET)
    public ResponseEntity<Comment> getComment(
            @Parameter(description = "조회할 댓글의 UUID", example = "123e4567-e89b-12d3-a456-12345678901", allowEmptyValue = false)
            @PathVariable(name = "id")
            UUID id
    ) {
        Comment body = commentService.getCommentById(id);
        return body != null
                ? ResponseEntity.ok(body) : ResponseEntity.notFound().build();
    }
    
    @Operation(
            summary = "댓글 1개 삭제",
            description = "특정 댓글을 DB에서 삭제합니다."
    )
    @ApiResponse(responseCode = "200 OK", description = "성공적으로 댓글을 삭제한 경우 반환")
    @RequestMapping(value = "/comment/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Boolean> deleteComment(
            @Parameter(description = "삭제할 댓글의 UUID", example = "123e4567-e89b-12d3-a456-12345678901", allowEmptyValue = false)
            @PathVariable(name = "id")
            UUID id
    ) {
        Comment targetComment = commentService.getCommentById(id);

        if (targetComment != null) {
            return commentService.deleteComment(id) ?
                    ResponseEntity.ok(Boolean.TRUE) : ResponseEntity.notFound().build();
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
        Issue targetIssue = issueService.getIssueById(issueId);
        List<Comment> body = new ArrayList<>();

        if (targetIssue != null) {
            if (!targetIssue.getCommentIds().isEmpty()) {
                targetIssue.getCommentIds().forEach(commentId -> {
                    body.add(commentService.getCommentById(commentId));
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
        Comment targetComment = commentService.getCommentById(id);

        if (targetComment != null) {
            if (patchCommentRequest.getBody() != null) targetComment.setBody(patchCommentRequest.getBody());

            return ResponseEntity.ok(targetComment);
        }

        return ResponseEntity.notFound().build();
    }
}