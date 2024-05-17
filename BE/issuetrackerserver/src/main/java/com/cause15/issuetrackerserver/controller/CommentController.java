package com.cause15.issuetrackerserver.controller;

import com.cause15.issuetrackerserver.model.Comment;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Tag(name = "Comment controller", description = "댓글 관련 API")
@RestController
@RequestMapping("/api")
public class CommentController {
    @Operation(
            summary = "특정 댓글의 데이터 조회",
            description = "특정 댓글의 데이터를 반환",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "특정 ID를 가지는 댓글 1개의 데이터 반환"
                    )
            }
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
}