/**
 * @ClassName MsgResponse
 * @Description
 * @author liyulong
 * @date 2022-05-12 14:58:19
 * @version 1.0
 * @since JDK1.8
 */

package com.xuexiang.templateproject.http.websocket;


/**
 * @Description
 * @author liyulong
 * @date 2022-05-12 14:58:19
 */
public class MsgResponse {

    private String sendUser;  //发送人id

    private String receiveUser; //接收人id

    private String roomId; //聊天房间id

    private String message;


    public String getSendUser() {
        return sendUser;
    }

    public void setSendUser(String sendUser) {
        this.sendUser = sendUser;
    }

    public String getReceiveUser() {
        return receiveUser;
    }

    public void setReceiveUser(String receiveUser) {
        this.receiveUser = receiveUser;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
