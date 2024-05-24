package com.cause15.issuetrackerserver.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class Project {
    private UUID id;
    private List<UUID> issueIds;
    private List<UUID> userIds;
    private String name;
    private LocalDateTime createdDate;
}
