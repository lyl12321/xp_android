package com.xuexiang.templateproject.http.user.api;

import com.xuexiang.templateproject.core.http.callback.TipCallBack;
import com.xuexiang.templateproject.http.user.entity.UserDTORes;
import com.xuexiang.xhttp2.XHttp;

public class UserApi {

    public static String register() {
        return "/user/insert";
    }

    public static String getUserInfo() {
        return "/user/getUserInfo";
    }
    public static void getUserInfo(TipCallBack<UserDTORes> callBack) {
        XHttp.post("/user/getUserInfo")
                .execute(callBack);
    }


    public static String updateUser() {
        return "/user/update";
    }

    public static String updatePass() {
        return "/user/updatePass";
    }
}
