package com.xuexiang.templateproject.http.login.api;

import com.xuexiang.templateproject.core.http.callback.TipCallBack;
import com.xuexiang.templateproject.http.login.entity.LoginResDTO;
import com.xuexiang.xhttp2.XHttp;

import java.util.HashMap;

public class LoginApi {

//    public static String login() {
//        return "/login";
//    }

    public static void login(String usernameOrPhone,String password,TipCallBack<LoginResDTO> callBack) {
        XHttp.post("/login")
                .params(new HashMap<String,Object>(){{
                    put("usernameOrPhone", usernameOrPhone);
                    put("password", password);
                }})
                .execute(callBack);
    }



    public static void forceLogout(String userId,TipCallBack<String> callBack) {
        XHttp.post("/forceLogout")
                .upString(userId)
                .execute(callBack);
    }

}
