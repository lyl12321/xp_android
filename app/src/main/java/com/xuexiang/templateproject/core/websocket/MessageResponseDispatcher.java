package com.xuexiang.templateproject.core.websocket;

import com.google.gson.reflect.TypeToken;
import com.xuexiang.templateproject.http.websocket.WebSocketResponseDTO;
import com.xuexiang.xutil.net.JsonUtil;
import com.zhangke.websocket.SimpleDispatcher;
import com.zhangke.websocket.dispatcher.ResponseDelivery;
import com.zhangke.websocket.response.ErrorResponse;
import com.zhangke.websocket.response.Response;
import com.zhangke.websocket.response.ResponseFactory;

public class MessageResponseDispatcher extends SimpleDispatcher {

    /**
     * JSON 数据格式错误
     */
    public static final int JSON_ERROR = 11;
    /**
     * code 码错误
     */
    public static final int CODE_ERROR = 12;

    @Override
    public void onMessage(String message, ResponseDelivery delivery) {
        WebSocketResponseDTO response = JsonUtil.fromJson(message, new TypeToken<WebSocketResponseDTO>() {
        }.getType());
        if (response.getType() != 0) {
            delivery.onMessage(message, response);
        } else {
            ErrorResponse errorResponse = ResponseFactory.createErrorResponse();
            errorResponse.setErrorCode(CODE_ERROR);
            Response<String> textResponse = ResponseFactory.createTextResponse();
            textResponse.setResponseData(message);
            errorResponse.setResponseData(textResponse);
            errorResponse.setReserved(response);
            onSendDataError(errorResponse, delivery);
        }
    }

    @Override
    public void onSendDataError(ErrorResponse error, ResponseDelivery delivery) {

    }
}
