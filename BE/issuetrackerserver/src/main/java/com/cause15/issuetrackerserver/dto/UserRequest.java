package com.cause15.issuetrackerserver.dto;

import com.cause15.issuetrackerserver.model.UserType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {
    private String name;
    private String password;
    private UserType type;
}