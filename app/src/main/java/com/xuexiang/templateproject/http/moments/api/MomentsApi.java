package com.xuexiang.templateproject.http.moments.api;

import com.xuexiang.templateproject.core.http.callback.TipCallBack;
import com.xuexiang.templateproject.http.moments.entity.MomentsListDTO;
import com.xuexiang.templateproject.utils.Constant;
import com.xuexiang.templateproject.utils.UserUtils;
import com.xuexiang.xhttp2.XHttp;
import com.xuexiang.xutil.net.JsonUtil;

import java.util.HashMap;
import java.util.Map;

public class MomentsApi {

    public static void publishMoment(String content, TipCallBack<String> callBack) {
        Map<String,Object> params = new HashMap<>();
        params.put("content",content);
        XHttp.post("/moments/insert")
                .upJson(JsonUtil.toJson(params))
                .execute(callBack);
    }

    public static void getMomentsList(Integer from, TipCallBack<MomentsListDTO> callBack) {
        getMomentsList(from, UserUtils.getCurrentUser().getClassCode(), callBack);
    }
    public static void getMomentsList(Integer from,String classCode, TipCallBack<MomentsListDTO> callBack) {
        Map<String,Object> params = new HashMap<>();
        params.put("publishClassCode",classCode);
        params.put("from",from);
        params.put("pageSize", Constant.pageSize);
        XHttp.post("/moments/page/query")
                .upJson(JsonUtil.toJson(params))
                .execute(callBack);
    }

}
