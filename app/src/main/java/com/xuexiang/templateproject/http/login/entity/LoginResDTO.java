package com.xuexiang.templateproject.http.login.entity;

import com.google.gson.annotations.SerializedName;
import com.xuexiang.templateproject.http.user.entity.UserDTORes;

public class LoginResDTO {

    @SerializedName("user")
    private UserDTORes user;
    @SerializedName("token")
    private String token;

    public UserDTORes getUser() {
        return user;
    }

    public void setUser(UserDTORes user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
