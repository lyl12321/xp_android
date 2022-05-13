package com.xuexiang.templateproject.http.grade.api;

import com.xuexiang.templateproject.core.http.callback.TipCallBack;
import com.xuexiang.templateproject.http.grade.entity.UserListDTO;
import com.xuexiang.templateproject.utils.Constant;
import com.xuexiang.xhttp2.XHttp;
import com.xuexiang.xutil.net.JsonUtil;

import java.util.HashMap;
import java.util.Map;

public class GradeApi {

//    public static String getClassMemberByUser() {
//        return "/class/getClassMemberByUser";
//    }
//
    public static void getClassMemberByUser(Integer from, TipCallBack<UserListDTO> callBack) {
        Map<String,Object> params = new HashMap<>();
        params.put("from",from);
        params.put("pageSize", Constant.pageSize);
        XHttp.post("/user/getClassMemberByUser")
                .upJson(JsonUtil.toJson(params))
                .execute(callBack);
    }



//    public static String deleteClassMember() {
//        return "/class/deleteById";
//    }


    public static void deleteClassMemberFun(String id, TipCallBack<String> callBack) {
        XHttp.post("/user/deleteClassCode")
                .upString(id)
                .execute(callBack);
    }


}
