package com.cause15.issuetrackerserver.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateCommentRequest {
    private String body;
    private UUID authorId;
}
