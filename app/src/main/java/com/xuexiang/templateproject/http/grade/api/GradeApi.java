package com.xuexiang.templateproject.http.grade.api;

import com.xuexiang.templateproject.core.http.callback.TipCallBack;
import com.xuexiang.xhttp2.XHttp;

public class GradeApi {

    public static String getClassMemberByUser() {
        return "/class/getClassMemberByUser";
    }


    public static String deleteClassMember() {
        return "/class/deleteById";
    }


    public static void deleteClassMemberFun(String id, TipCallBack<String> callBack) {
        XHttp.post(GradeApi.deleteClassMember())
                .upString(id)
                .execute(callBack);
    }


}
