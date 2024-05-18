package com.cause15.issuetrackerserver.dto;


import com.cause15.issuetrackerserver.model.IssueType;
import com.cause15.issuetrackerserver.model.Tester;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IssueRequest {
    private String title;
    private String description;
    private IssueType type;
    private Tester reporter;
}
