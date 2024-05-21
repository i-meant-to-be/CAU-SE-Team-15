package com.cause15.issuetrackerserver.controller;

import com.cause15.issuetrackerserver.model.Comment;
import com.cause15.issuetrackerserver.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Tag(name = "Comment Controller", description = "댓글 관련 API")
@RestController
@RequestMapping("/api")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService){
        this.commentService = commentService;
    }

    @Operation(
            summary = "전체 댓글 조회",
            description = "전체 댓글의 데이터를 반환합니다."
    )
    @RequestMapping(value = "/comment", method = RequestMethod.GET)
    public List<Comment> getAllComments() {
        return commentService.getAllComments();
    }

    @Operation(
            summary = "특정 댓글 조회",
            description = "특정 댓글의 데이터를 반환합니다."
    )
    @RequestMapping(value = "/comment/{id}", method = RequestMethod.GET)
    public Comment getComment(
            @Parameter(description = "조회할 댓글의 UUID", example = "123e4567-e89b-12d3-a456-12345678901", allowEmptyValue = false)
            @PathVariable(name = "id")
            UUID id
    ) {
        // TODO: DB에서 가져오는 코드로 수정 필요
        return new Comment();
    }
    
    @Operation(
            summary = "댓글 1개 삭제",
            description = "특정 댓글을 DB에서 삭제합니다."
    )
    @RequestMapping(value = "/comment/{id}", method = RequestMethod.DELETE)
    public Comment deleteComment(
            @Parameter(description = "삭제할 댓글의 UUID", example = "123e4567-e89b-12d3-a456-12345678901", allowEmptyValue = false)
            @PathVariable(name = "id")
            UUID id
    ) {
        // TODO: DB에서 가져오는 코드로 수정 필요
        return new Comment();
    }
}