package com.cause15.issuetrackerserver.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class CreateProjectRequest {
    private String title;
    private String description;
    private List<UUID> userIds;
}
