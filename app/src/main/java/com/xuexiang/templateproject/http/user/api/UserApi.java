package com.xuexiang.templateproject.http.user.api;

public class UserApi {

    public static String register() {
        return "/user/insert";
    }

    public static String getUserInfo() {
        return "/user/getUserInfo";
    }


    public static String updateUser() {
        return "/user/update";
    }

    public static String updatePass() {
        return "/user/updatePass";
    }
}
