package com.xuexiang.templateproject.http.order.api;

import com.xuexiang.templateproject.core.http.callback.TipCallBack;
import com.xuexiang.templateproject.http.order.entity.GoodsOrderListDTO;
import com.xuexiang.templateproject.http.wallet.entity.WalletListDTO;
import com.xuexiang.templateproject.utils.Constant;
import com.xuexiang.xhttp2.XHttp;
import com.xuexiang.xutil.net.JsonUtil;

import java.util.HashMap;
import java.util.Map;

public class OrderApi {

    public static void hasThisGoods(String id, TipCallBack<Boolean> callBack) {
        XHttp.post("/order/hasGoods")
                .upString(id)
                .execute(callBack);
    }

    public static void getGoodsOrderList(Integer from, TipCallBack<GoodsOrderListDTO> callBack) {
        Map<String,Object> params = new HashMap<>();
        params.put("from",from);
        params.put("pageSize", Constant.pageSize);
        XHttp.post("/order/page/query")
                .upJson(JsonUtil.toJson(params))
                .execute(callBack);
    }

}
