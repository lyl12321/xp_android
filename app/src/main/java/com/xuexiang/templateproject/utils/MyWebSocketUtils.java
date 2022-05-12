package com.xuexiang.templateproject.utils;

import com.xuexiang.templateproject.core.websocket.MessageResponseDispatcher;
import com.xuexiang.templateproject.http.user.entity.UserDTORes;
import com.xuexiang.xutil.common.StringUtils;
import com.zhangke.websocket.WebSocketHandler;
import com.zhangke.websocket.WebSocketManager;
import com.zhangke.websocket.WebSocketSetting;

public class MyWebSocketUtils {


    public static void initWebSocket() {
        UserDTORes currentUser = UserUtils.getCurrentUser();
        //有登陆用户的时候  开始初始化ws
        if (currentUser != null && !StringUtils.isEmpty(currentUser.getId())) {

            WebSocketSetting setting = new WebSocketSetting();
            //连接地址，必填，例如 wss://localhost:8080c
            setting.setConnectUrl(Constant.apiBaseUrl + "/message_websocket/" + currentUser.getId());


            //设置连接超时时间
            setting.setConnectTimeout(10 * 1000);

            //设置心跳间隔时间
            setting.setConnectionLostTimeout(30);

            //设置断开后的重连次数，可以设置的很大，不会有什么性能上的影响
            setting.setReconnectFrequency(40);

            //设置 Headers
//            setting.setHttpHeaders(header);

            //设置消息分发器，接收到数据后先进入该类中处理，处理完再发送到下游
            setting.setResponseProcessDispatcher(new MessageResponseDispatcher());
            //接收到数据后是否放入子线程处理，只有设置了 ResponseProcessDispatcher 才有意义
            setting.setProcessDataOnBackground(true);

            //网络状态发生变化后是否重连，
            //需要调用 WebSocketHandler.registerNetworkChangedReceiver(context) 方法注册网络监听广播
            setting.setReconnectWithNetworkChanged(true);

            WebSocketManager init = WebSocketHandler.init(setting);
            init.start();
        }

    }


    /*
    销毁websocket  可以用于用户退出登陆时
     */
    public static void destroyWebSocket() {
        WebSocketManager manager = WebSocketHandler.getDefault();
        manager.destroy();
    }

}
