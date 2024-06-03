package com.cause15.issuetrackerapp.data.dto

import com.cause15.issuetrackerapp.util.enums.UserType

class LoginDTO(
    var username: String,
    var password: String
)

class CreateUserDTO (
    var username: String,
    var password: String,
    var type: UserType
)