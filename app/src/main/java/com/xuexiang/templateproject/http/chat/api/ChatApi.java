package com.xuexiang.templateproject.http.chat.api;

import com.xuexiang.templateproject.core.http.callback.TipCallBack;
import com.xuexiang.templateproject.http.chat.entity.ChatRoomListDTO;
import com.xuexiang.templateproject.utils.Constant;
import com.xuexiang.templateproject.utils.UserUtils;
import com.xuexiang.xhttp2.XHttp;
import com.xuexiang.xutil.net.JsonUtil;

import java.util.HashMap;
import java.util.Map;

public class ChatApi {

    public static void getChatRoomList(Integer from, TipCallBack<ChatRoomListDTO> callBack) {
        Map<String,Object> params = new HashMap<>();
        params.put("currentUserId", UserUtils.getCurrentUser().getId());
        params.put("from",from);
        params.put("pageSize", 1000);
        XHttp.post("/chatRoom/page/query")
                .upJson(JsonUtil.toJson(params))
                .execute(callBack);
    }
}
