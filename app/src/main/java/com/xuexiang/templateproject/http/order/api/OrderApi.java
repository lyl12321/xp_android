package com.xuexiang.templateproject.http.order.api;

import com.xuexiang.templateproject.core.http.callback.TipCallBack;
import com.xuexiang.xhttp2.XHttp;

public class OrderApi {

    public static void hasThisGoods(String id, TipCallBack<Boolean> callBack) {
        XHttp.post("/order/hasGoods")
                .upString(id)
                .execute(callBack);
    }
}
