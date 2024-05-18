package com.cause15.issuetrackerserver.util;

import com.cause15.issuetrackerserver.model.User;
import com.google.gson.Gson;

public class Converter {
    private static final Gson gson = new Gson();

    public User convertStringToUser(String jsonString) {
        return gson.fromJson(jsonString, User.class);
    }

    public String convertUserToString(User user) {
        return gson.toJson(user);
    }
}
