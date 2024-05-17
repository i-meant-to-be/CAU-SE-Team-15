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

@Tag(name = "User controller", description = "사용자 관련 API")
@RestController
public class UserController {
    @Operation(
            summary = "사용자 데이터 조회",
            description = "특정 사용자의 데이터를 반환합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "특정 ID를 가지는 사용자 1명의 데이터 반환"
                    )
            }
    )
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public User getUser(
            @Parameter(description = "조회할 사용자의 UUID", example = "123e4567-e89b-12d3-a456-12345678901", allowEmptyValue = false)
            @PathVariable(name = "id")
            UUID id
    ) {
        // TODO: DB에서 가져오는 코드로 수정 필요
        return new User();
    }

    @Operation(
            summary = "사용자 삭제",
            description = "특정 사용자를 DB에서 삭제합니다."
    )
    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    public boolean deleteUser(
            @Parameter(description = "삭제할 사용자의 UUID", example = "123e4567-e89b-12d3-a456-12345678901", allowEmptyValue = false)
            @PathVariable(name = "id")
            UUID id
    ) {
        // TODO: DB에서 삭제하는 코드로 수정 필요
        return true;
    }
}
