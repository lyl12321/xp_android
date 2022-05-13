package com.xuexiang.templateproject.http.chat.api;

import com.xuexiang.templateproject.core.http.callback.TipCallBack;
import com.xuexiang.templateproject.http.chat.entity.ChatRoomListDTO;
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

    public static void clearNoReadMessage(String roomId, TipCallBack<String> callBack) {
        Map<String,Object> params = new HashMap<>();
        params.put("receiveId", UserUtils.getCurrentUser().getId());
        params.put("roomId", roomId);
        XHttp.post("/chatRoom/clearNoReadMessage")
                .upJson(JsonUtil.toJson(params))
                .execute(callBack);
    }

    public static void getAllNoReadMessageCount(TipCallBack<Integer> callBack) {
        XHttp.post("/chats/getAllNoReadMessageCount")
                .execute(callBack);
    }

    public static void createChatRoom(String receiveId,TipCallBack<ChatRoomListDTO.ChatRoomItem> callBack) {
        XHttp.post("/chatRoom/createChatRoom")
                .upString(receiveId)
                .execute(callBack);
    }
}
