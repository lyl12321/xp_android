
package com.xuexiang.templateproject.core.http.callback;

import com.xuexiang.templateproject.utils.TokenUtils;
import com.xuexiang.templateproject.utils.XToastUtils;
import com.xuexiang.xhttp2.callback.SimpleCallBack;
import com.xuexiang.xhttp2.exception.ApiException;
import com.xuexiang.xhttp2.model.XHttpRequest;
import com.xuexiang.xutil.common.StringUtils;
import com.xuexiang.xutil.common.logger.Logger;

/**
 * 带错误toast提示的网络请求回调
 *

 * @since 2019-11-18 23:02
 */
public abstract class TipCallBack<T> extends SimpleCallBack<T> {

    /**
     * 记录一下请求的url,确定出错的请求是哪个请求
     */
    private String mUrl;

    public TipCallBack() {

    }

    public TipCallBack(XHttpRequest req) {
        this(req.getUrl());
    }

    public TipCallBack(String url) {
        mUrl = url;
    }

    @Override
    public void onError(ApiException e) {
        if ("sessionExpired".equals(e.getMessage())) {
            XToastUtils.error("登陆过期！");
            TokenUtils.forceLogoutSuccess();
        } else if (e.getCode() != 5010){  //把空指针错误取消提醒 不处理5010 Code
            XToastUtils.error(e);
        }

        if (!StringUtils.isEmpty(mUrl)) {
            Logger.e("网络请求的url:" + mUrl, e);
        } else {
            Logger.e(e);
        }
    }

}
