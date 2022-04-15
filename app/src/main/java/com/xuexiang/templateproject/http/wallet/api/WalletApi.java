package com.xuexiang.templateproject.http.wallet.api;

import com.xuexiang.templateproject.core.http.callback.TipCallBack;
import com.xuexiang.templateproject.http.wallet.entity.WalletListDTO;
import com.xuexiang.templateproject.utils.Constant;
import com.xuexiang.xhttp2.XHttp;
import com.xuexiang.xutil.net.JsonUtil;

import java.util.HashMap;
import java.util.Map;

public class WalletApi {


    public static void buyGoods(String id, TipCallBack<String> callBack) {
        XHttp.post("/wallet/buyGoods")
                .upString(id)
                .execute(callBack);
    }

    public static void getMyBalance(TipCallBack<Integer> callBack) {
        XHttp.post("/wallet/getMyBalance")
                .execute(callBack);
    }


    public static void queryPage(Integer from, TipCallBack<WalletListDTO> callBack) {
        Map<String,Object> params = new HashMap<>();
        params.put("from",from);
        params.put("pageSize", Constant.pageSize);
        XHttp.post("/wallet/page/query")
                .upJson(JsonUtil.toJson(params))
                .execute(callBack);
    }


    public static void verifyCard(String cardCode, TipCallBack<String> callBack) {
        XHttp.post("/wallet/verifyCard")
                .upString(cardCode)
                .execute(callBack);
    }
}
