
package com.xuexiang.templateproject;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

import com.xuexiang.templateproject.utils.sdkinit.ANRWatchDogInit;
import com.xuexiang.templateproject.utils.sdkinit.UMengInit;
import com.xuexiang.templateproject.utils.sdkinit.XBasicLibInit;
import com.xuexiang.templateproject.utils.sdkinit.XUpdateInit;

/**

 * @since 2018/11/7 下午1:12
 */
public class MyApp extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //解决4.x运行崩溃的问题
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initLibs();
    }

    /**
     * 初始化基础库
     */
    private void initLibs() {
        // X系列基础库初始化
        XBasicLibInit.init(this);
        // 版本更新初始化
        XUpdateInit.init(this);
        // 运营统计数据
        UMengInit.init(this);
        // ANR监控
        ANRWatchDogInit.init();
//
//        HttpSDK.init(this);   //初始化网络请求框架，必须首先执行
//        XHttpSDK.debug("XHttp");  //需要调试的时候执行
//        XHttpSDK.setBaseUrl(SettingSPUtils.getInstance().getApiURL());  //设置网络请求的基础地址
    }


    /**
     * @return 当前app是否是调试开发模式
     */
    public static boolean isDebug() {
        return BuildConfig.DEBUG;
    }


}
