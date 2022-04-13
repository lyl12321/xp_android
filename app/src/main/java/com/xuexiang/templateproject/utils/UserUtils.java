package com.xuexiang.templateproject.utils;

import com.xuexiang.templateproject.http.user.entity.UserDTORes;
import com.xuexiang.xpage.utils.GsonUtils;

public class UserUtils {

    public static UserDTORes getCurrentUser() {
        return GsonUtils.fromJson((String) MMKVUtils.get(TokenUtils.getToken(), "{}"), UserDTORes.class);
    }
}
