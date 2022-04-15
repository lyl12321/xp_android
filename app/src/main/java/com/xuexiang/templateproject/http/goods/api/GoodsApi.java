package com.xuexiang.templateproject.http.goods.api;

import com.xuexiang.templateproject.core.http.callback.TipCallBack;
import com.xuexiang.templateproject.http.goods.entity.GoodsListDTO;
import com.xuexiang.xhttp2.XHttp;

public class GoodsApi {

    public static String queryGoodsPages() {
        return "/goods/page/query";
    }




    public static void getGoodsInfo(String id, TipCallBack<GoodsListDTO.GoodsDTO> callBack) {
        XHttp.post("/goods/getById")
                .upString(id)
                .execute(callBack);
    }


}
