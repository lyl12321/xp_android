package com.xuexiang.templateproject.utils;

import java.util.HashMap;
import java.util.Map;


/*
商品分类代码和代码翻译
 */
public class GoodsClassifyUtils {

    public static Map<String,String> classify = new HashMap<>();

    static {
        classify.put("00","名师精品课");
        classify.put("01","1对1课");
        classify.put("02","拼单课");
        classify.put("03","活动优惠");



        //反过来再放一次  这样就可以直接相互转换
        Map<String,String> reverse = new HashMap<>();
        for (Map.Entry<String, String> entry : classify.entrySet()) {
            reverse.put(entry.getValue(),entry.getKey());
        }
        classify.putAll(reverse);
    }


    public static String translationClassify(String classifyNameOrId) {
        return classify.get(classifyNameOrId);
    }

}
