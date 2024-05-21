package com.cause15.issuetrackerserver.controller;

import com.cause15.issuetrackerserver.dto.UserRequest;
import com.cause15.issuetrackerserver.model.User;
import com.cause15.issuetrackerserver.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "User Controller", description = "사용자 관련 API")
@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(
            summary = "새로운 사용자 추가",
            description = "새로운 사용자를 DB에 추가합니다."
    )
    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public User createUser(
            @RequestParam String name,
            @RequestParam String password
    ) {
        User newUser = new User(name, password);
        return userService.createUser(newUser);
    }

    @Operation(
            summary = "사용자 1명의 데이터 조회",
            description = "특정 사용자의 데이터를 반환합니다."
    )
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public User getUser(
            @Parameter(description = "조회할 사용자의 UUID", example = "123e4567-e89b-12d3-a456-12345678901", allowEmptyValue = false)
            @PathVariable(name = "id")
            UUID id
    ) {
        // TODO: DB에서 가져오는 코드로 수정 필요
        return userService.getUserById(id);
    }

    @Operation(
            summary = "사용자 1명의 데이터 수정",
            description = "특정 사용자의 데이터를 수정합니다."
    )
    @RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
    public User setUser(
            @Parameter(description = "수정할 사용자의 UUID", example = "123e4567-e89b-12d3-a456-12345678901", allowEmptyValue = false)
            @PathVariable(name = "id")
            UUID id
    ) {
        // TODO: DB에서 가져오는 코드로 수정 필요
        return new User();
    }

    @Operation(
            summary = "사용자 전체의 데이터 조회",
            description = "특정 사용자의 데이터를 반환합니다."
    )
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public List<User> getAllUsers() {
       return userService.getAllUsers();
    }

    @Operation(
            summary = "사용자 1명 삭제",
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

    @Operation(
            summary = "로그인",
            description = "사용자 이름과 비밀번호로 서비스에 로그인합니다."
    )
    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    public boolean login(
            @RequestParam String name,
            @RequestParam String password
    ) {
        // TODO: DB에서 삭제하는 코드로 수정 필요
        return true;
    }
}
