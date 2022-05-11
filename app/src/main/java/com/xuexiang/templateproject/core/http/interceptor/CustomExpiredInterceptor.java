package com.xuexiang.templateproject.core.http.interceptor;

import com.xuexiang.templateproject.utils.TokenUtils;
import com.xuexiang.xhttp2.interceptor.BaseExpiredInterceptor;
import com.xuexiang.xhttp2.model.ExpiredInfo;
import com.xuexiang.xhttp2.utils.HttpUtils;
import com.xuexiang.xutil.net.JSONUtils;

import okhttp3.Response;

public class CustomExpiredInterceptor extends BaseExpiredInterceptor {

    private final int TOKEN_MISSING = -1;

    private final int KEY_TOKEN_EXPIRED = 1;

    @Override
    protected ExpiredInfo isResponseExpired(Response oldResponse, String bodyString) {

        int code = JSONUtils.getInt(bodyString, "code", -1);
        ExpiredInfo expiredInfo = new ExpiredInfo(code);

        if (code == TOKEN_MISSING) {
            expiredInfo.setExpiredType(KEY_TOKEN_EXPIRED)
                    .setBodyString(bodyString);
        }
        return expiredInfo;
    }

    @Override
    protected Response responseExpired(Response oldResponse, Chain chain, ExpiredInfo expiredInfo) {
        if (expiredInfo.getExpiredType() == KEY_TOKEN_EXPIRED) {//token失效了
            //提示 + 退出登陆
//            XUtil.getActivityLifecycleHelper().exit();
            TokenUtils.forceLogoutSuccess();
            return HttpUtils.getErrorResponse(oldResponse, expiredInfo.getCode(), "登陆过期！");
        }
        return null;
    }
}
