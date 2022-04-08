
package com.xuexiang.templateproject.activity;

import android.view.KeyEvent;

import com.xuexiang.templateproject.R;
import com.xuexiang.templateproject.core.http.callback.NoTipCallBack;
import com.xuexiang.templateproject.http.user.api.UserService;
import com.xuexiang.templateproject.http.user.entity.UserDTORes;
import com.xuexiang.templateproject.utils.MMKVUtils;
import com.xuexiang.templateproject.utils.TokenUtils;
import com.xuexiang.xhttp2.XHttp;
import com.xuexiang.xhttp2.cache.model.CacheMode;
import com.xuexiang.xhttp2.request.CustomRequest;
import com.xuexiang.xpage.utils.GsonUtils;
import com.xuexiang.xui.utils.KeyboardUtils;
import com.xuexiang.xui.widget.activity.BaseSplashActivity;
import com.xuexiang.xutil.app.ActivityUtils;

import me.jessyan.autosize.internal.CancelAdapt;

/**
 * 启动页
 *
 */
public class SplashActivity extends BaseSplashActivity implements CancelAdapt {

    @Override
    protected long getSplashDurationMillis() {
        return 500;
    }

    /**
     * activity启动后的初始化
     */
    @Override
    protected void onCreateActivity() {
        initSplashView(R.drawable.xui_config_bg_splash);
        startSplash(false);
    }


    /**
     * 启动页结束后的动作
     */
    @Override
    protected void onSplashFinished() {

//        if (SettingUtils.isAgreePrivacy()) {
            loginOrGoMainPage();
//        } else {
//            Utils.showPrivacyDialog(this, (dialog, which) -> {
//                dialog.dismiss();
//                SettingUtils.setIsAgreePrivacy(true);
//                loginOrGoMainPage();
//            });
//        }
    }

    private void loginOrGoMainPage() {
        if (TokenUtils.hasToken()) {
            CustomRequest request = XHttp.custom().cacheMode(CacheMode.NO_CACHE);
            request.apiCall(request.create(UserService.class).getUserInfo(), new NoTipCallBack<UserDTORes>() {
                @Override
                public void onSuccess(UserDTORes response) throws Throwable {
                    if (response.getId() == null) {
                        TokenUtils.handleLogoutSuccess();
                        ActivityUtils.startActivity(LoginActivity.class);
                    } else {
                        MMKVUtils.put(TokenUtils.getToken(), GsonUtils.toJson(response));
                        ActivityUtils.startActivity(MainActivity.class);
                    }
                }
            });

        } else {
            ActivityUtils.startActivity(LoginActivity.class);
        }
        finish();
    }

    /**
     * 菜单、返回键响应
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return KeyboardUtils.onDisableBackKeyDown(keyCode) && super.onKeyDown(keyCode, event);
    }
}
