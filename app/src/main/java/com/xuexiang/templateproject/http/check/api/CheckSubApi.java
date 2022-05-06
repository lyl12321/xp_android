package com.xuexiang.templateproject.http.check.api;

import com.xuexiang.templateproject.core.http.callback.TipCallBack;
import com.xuexiang.templateproject.http.check.entity.CheckDetailsDTO;
import com.xuexiang.templateproject.http.check.entity.CheckUserListDTO;
import com.xuexiang.templateproject.utils.Constant;
import com.xuexiang.xhttp2.XHttp;
import com.xuexiang.xutil.net.JsonUtil;

import java.util.HashMap;
import java.util.Map;

public class CheckSubApi {
    //获取签到表单信息详情
    public static void getCheckDetails(String checkPid,String userId, TipCallBack<CheckDetailsDTO> callBack) {
        Map<String,Object> params = new HashMap<>();
        params.put("id",checkPid);
        params.put("userId",userId);
        XHttp.post("/checkSub/getCheckDetails")
                .upJson(JsonUtil.toJson(params))
                .execute(callBack);
    }

    public static void submitCheckInfo(String checkPid, Map<String,Object> params, TipCallBack<String> callBack) {
        params.put("checkPid",checkPid);
        XHttp.post("/checkSub/insert")
                .upJson(JsonUtil.toJson(params))
                .execute(callBack);
    }


    public static void queryCheckUserList(Integer from,String checkPid,String classCode, TipCallBack<CheckUserListDTO> callBack) {
        Map<String,Object> params = new HashMap<>();
        params.put("from",from);
        params.put("pageSize", Constant.pageSize);
        params.put("pid",checkPid);
        params.put("classCode",classCode);
        XHttp.post("/checkSub/page/query")
                .upJson(JsonUtil.toJson(params))
                .execute(callBack);
    }
}
