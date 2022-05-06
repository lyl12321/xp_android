package com.xuexiang.templateproject.http.goods.api;

import com.xuexiang.templateproject.core.http.callback.TipCallBack;
import com.xuexiang.templateproject.http.goods.entity.GoodsListDTO;
import com.xuexiang.templateproject.utils.Constant;
import com.xuexiang.templateproject.utils.GoodsClassifyUtils;
import com.xuexiang.xhttp2.XHttp;
import com.xuexiang.xutil.net.JsonUtil;

import java.util.HashMap;
import java.util.Map;

public class GoodsApi {

//    public static String queryGoodsPages() {
//        return "/goods/page/query";
//    }

    public static void queryGoodsPages(Integer from,String searchKey, TipCallBack<GoodsListDTO> callBack) {
        Map<String,Object> params = new HashMap<>();
        params.put("from",from);
        params.put("pageSize", Constant.pageSize);
        params.put("searchKey", searchKey);
        queryGoodsPages(params,callBack);
    }
    public static void queryGoodsPages(Integer from,String searchKey,String classification ,TipCallBack<GoodsListDTO> callBack) {
        Map<String,Object> params = new HashMap<>();
        params.put("from",from);
        params.put("pageSize", Constant.pageSize);
        params.put("searchKey", searchKey);
        params.put("classification", GoodsClassifyUtils.translationClassify(classification));
        queryGoodsPages(params,callBack);
    }

    public static void queryGoodsPages(Map<String,Object> params,TipCallBack<GoodsListDTO> callBack) {
        XHttp.post("/goods/page/query")
                .upJson(JsonUtil.toJson(params))
                .execute(callBack);
    }


    public static void getGoodsInfo(String id, TipCallBack<GoodsListDTO.GoodsDTO> callBack) {
        XHttp.post("/goods/getById")
                .upString(id)
                .execute(callBack);
    }


}
