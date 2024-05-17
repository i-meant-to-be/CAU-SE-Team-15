package com.cause15.issuetrackerserver.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import com.cause15.issuetrackerserver.model.User;

@Tag(name = "사용자", description = "사용자 관련 API")
@RestController
public class UserController {
    @Operation(
            summary = "사용자 조회",
            description = "모든 사용자의 정보를 반환",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "조회된 사용자 정보를 반환"
                    )
            }
    )
    @RequestMapping(value = "/user/getUser/{id}", method = RequestMethod.GET)
    public User getUser(
            @Parameter(description = "조회할 사용자의 UUID")
            @PathVariable(name = "id")
            UUID id
    ) {
        // TODO: DB에서 가져오는 코드로 수정 필요
        return new User(id, "John", "");
    }
}
