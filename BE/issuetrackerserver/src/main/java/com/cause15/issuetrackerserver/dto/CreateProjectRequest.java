package com.cause15.issuetrackerserver.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateProjectRequest {
    private String title;
    private String description;
}
