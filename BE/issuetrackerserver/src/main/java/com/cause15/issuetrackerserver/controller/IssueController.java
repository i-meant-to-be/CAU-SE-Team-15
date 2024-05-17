package com.cause15.issuetrackerserver.controller;

import com.cause15.issuetrackerserver.model.Issue;
import com.cause15.issuetrackerserver.service.IssueService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Tag(name = "Issue controller", description = "이슈 관련 API")
@RestController
@RequestMapping("/api")
public class IssueController {
    private final IssueService issueService;

    public IssueController(IssueService issueService) {
        this.issueService = issueService;
    }

    @Operation(
            summary = "특정 이슈의 데이터 조회",
            description = "특정 이슈의 데이터를 반환",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "특정 ID를 가지는 이슈 1개의 데이터 반환"
                    )
            }
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

    /*    예시
    @GetMapping("/issue")
    public ResponseEntity<List<Issue>> getAllIssues() {
        return ResponseEntity.ok(issueService.getAllIssues());
    }

    @PostMapping("/issue")
    public ResponseEntity<Issue>CreateIssue(@RequestBody Issue issue){
        Issue newIssue = issueService.createIssue(issue);
        return ResponseEntity.ok(newIssue);
    }
    */

}
