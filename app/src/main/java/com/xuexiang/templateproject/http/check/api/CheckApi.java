package com.xuexiang.templateproject.http.check.api;

import com.xuexiang.templateproject.core.http.callback.TipCallBack;
import com.xuexiang.templateproject.http.check.entity.CheckListDTO;
import com.xuexiang.templateproject.utils.Constant;
import com.xuexiang.xhttp2.XHttp;
import com.xuexiang.xutil.net.JsonUtil;

import java.util.HashMap;
import java.util.Map;

public class CheckApi {

    public static void getCheckList(Integer from,TipCallBack<CheckListDTO> callBack) {
        Map<String,Object> params = new HashMap<>();
        params.put("from",from);
        params.put("pageSize", Constant.pageSize);
        XHttp.post("/checkMain/page/query")
                .upJson(JsonUtil.toJson(params))
                .execute(callBack);
    }
}
