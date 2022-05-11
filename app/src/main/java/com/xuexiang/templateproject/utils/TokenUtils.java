
package com.xuexiang.templateproject.utils;

import android.content.Context;

import com.umeng.analytics.MobclickAgent;
import com.xuexiang.templateproject.activity.LoginActivity;
import com.xuexiang.templateproject.core.http.interceptor.CustomExpiredInterceptor;
import com.xuexiang.xhttp2.XHttp;
import com.xuexiang.xhttp2.model.HttpHeaders;
import com.xuexiang.xutil.app.ActivityUtils;
import com.xuexiang.xutil.common.StringUtils;

/**
 * Token管理工具
 *

 * @since 2019-11-17 22:37
 */
public final class TokenUtils {

    private static String sToken;

    private static final String KEY_TOKEN = "com.xp.utils.KEY_TOKEN";

    private TokenUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    private static final String KEY_PROFILE_CHANNEL = "other";

    /**
     * 初始化Token信息
     */
    public static void init(Context context) {
        MMKVUtils.init(context);
        sToken = MMKVUtils.getString(KEY_TOKEN, "");
    }

    public static void setToken(String token) {
        sToken = token;
        MMKVUtils.put(KEY_TOKEN, token);
    }

    public static void clearToken() {
        sToken = null;
        MMKVUtils.remove(KEY_TOKEN);
    }

    public static String getToken() {
        return sToken;
    }

    public static boolean hasToken() {
        return MMKVUtils.containsKey(KEY_TOKEN);
    }

    /**
     * 处理登录成功的事件
     *
     * @param token 账户信息
     */
    public static boolean handleLoginSuccess(String token) {
        if (!StringUtils.isEmpty(token)) {
            XToastUtils.success("登录成功！");
            MobclickAgent.onProfileSignIn(KEY_PROFILE_CHANNEL, token);
            setToken(token);
            XHttp.getInstance()
                    .addInterceptor(new CustomExpiredInterceptor())
                    .addCommonHeaders(new HttpHeaders("X-Token",TokenUtils.getToken()));
            return true;
        } else {
            XToastUtils.error("登录失败！");
            return false;
        }
    }

    /**
     * 处理登出的事件
     */
    public static void handleLogoutSuccess() {
        MobclickAgent.onProfileSignOff();
        MMKVUtils.remove(TokenUtils.getToken());
        //登出时，清除账号信息
        clearToken();
        XToastUtils.success("登出成功！");
//        SettingUtils.setIsAgreePrivacy(false);
        XHttp.getInstance().setStrictMode(false)
                .addCommonHeaders(new HttpHeaders("X-Token",TokenUtils.getToken()));  //退出登陆时清空统一请求头
        //跳转到登录页
        ActivityUtils.startActivity(LoginActivity.class);
    }

    public static void forceLogoutSuccess() {  ///只是不显示提示的登出
        MobclickAgent.onProfileSignOff();
        MMKVUtils.remove(TokenUtils.getToken());
        //登出时，清除账号信息
        clearToken();
//        SettingUtils.setIsAgreePrivacy(false);
        XHttp.getInstance().setStrictMode(false)
                .addCommonHeaders(new HttpHeaders("X-Token",TokenUtils.getToken()));  //退出登陆时清空统一请求头
        //跳转到登录页
        ActivityUtils.startActivity(LoginActivity.class);
    }
}
