package com.cause15.issuetrackerserver.controller;

import com.cause15.issuetrackerserver.model.*;
import com.cause15.issuetrackerserver.service.IssueService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "Issue Controller", description = "이슈 관련 API")
@RestController
@RequestMapping("/api")
public class IssueController {

    @Autowired
    private IssueService issueService;

    @Operation(
            summary = "새로운 이슈 추가",
            description = "새로운 이슈를 DB에 추가합니다."
    )
    @RequestMapping(value = "/issue", method = RequestMethod.POST)
    public Issue createIssue(
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam UUID reporterId,
            @RequestParam(required = false) IssueType type
    ) {
        // TODO: DB에서 가져오는 코드로 수정 필요
        Tester reporter = new Tester(reporterId, "Reporter", "pwd");
        return new Issue(title, description, type, reporter);
    }

    @Operation(
            summary = "이슈 1건의 데이터 조회",
            description = "특정한 이슈의 데이터를 반환합니다."
    )
    @RequestMapping(value = "/issue/{id}", method = RequestMethod.GET)
    public Issue getIssue(
        @Parameter(description = "조회할 이슈의 UUID", example = "123e4567-e89b-12d3-a456-12345678901", allowEmptyValue = false)
        @PathVariable(name = "id")
        UUID id
    ) {
        // TODO: DB에서 가져오는 코드로 수정 필요
        return new Issue();
    }

    @Operation(
            summary = "이슈 1건의 데이터 수정",
            description = "특정한 이슈의 데이터를 수정합니다."
    )
    @RequestMapping(value = "/issue/{id}", method = RequestMethod.PUT)
    public Issue setIssue(
            @Parameter(description = "수정할 이슈의 UUID", example = "123e4567-e89b-12d3-a456-12345678901", allowEmptyValue = false)
            @PathVariable(name = "id")
            UUID id
    ) {
        // TODO: DB에서 가져오는 코드로 수정 필요
        return new Issue();
    }


    @Operation(
            summary = "이슈 1건의 데이터 조회",
            description = "특정한 이슈의 데이터를 반환합니다."
    )
    @RequestMapping(value = "/issue", method = RequestMethod.GET)
    public Issue[] getAllIssues() {
        // TODO: DB에서 가져오는 코드로 수정 필요
        return new Issue[10];
    }

    @Operation(
            summary = "이슈 1건 삭제",
            description = "특정 이슈를 DB에서 삭제합니다."
    )
    @RequestMapping(value = "/issue/{id}", method = RequestMethod.DELETE)
    public boolean deleteIssue(
            @Parameter(description = "삭제할 이슈의 UUID", example = "123e4567-e89b-12d3-a456-12345678901", allowEmptyValue = false)
            @PathVariable(name = "id")
            UUID id
    ) {
        // TODO: DB에서 가져오는 코드로 수정 필요
        return true;
    }

    @Operation(
            summary = "특정 이슈의 모든 댓글 조회",
            description = "특정 이슈와 관련하여 작성된 모든 댓글을 반환합니다."
    )
    @RequestMapping(value = "/issue/{id}/comment", method = RequestMethod.POST)
    public Comment addCommentToIssue(
            @Parameter(description = "댓글을 확인할 이슈의 ID", example = "123e4567-e89b-12d3-a456-12345678901", allowEmptyValue = false)
            @RequestParam String body,
            @RequestParam UUID userId
    ) {
        // TODO: DB에서 가져오는 코드로 수정 필요
        User author = new User(userId, "User", "pwd");
        return new Comment(body, author);
    }

    @Operation(
            summary = "특정 이슈에 댓글 1개 추가",
            description = "특정 이슈에 새로운 댓글을 추가합니다."
    )
    @RequestMapping(value = "/issue/{id}/comment", method = RequestMethod.GET)
    public Comment[] getAllComments(
            @Parameter(description = "댓글을 확인할 이슈의 ID", example = "123e4567-e89b-12d3-a456-12345678901", allowEmptyValue = false)
            @PathVariable(name = "id")
            UUID id
    ) {
        // TODO: DB에서 가져오는 코드로 수정 필요
        return new Issue().getComments();
    }


}
