package com.cause15.issuetrackerserver.dto;

import com.cause15.issuetrackerserver.model.IssueState;
import com.cause15.issuetrackerserver.model.IssueType;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class PatchIssueRequest {
    private String title;
    private String description;
    private IssueType type;
    private UUID reporterId;
    private UUID assigneeId;
    private UUID fixerId;
    private IssueState state;
}
