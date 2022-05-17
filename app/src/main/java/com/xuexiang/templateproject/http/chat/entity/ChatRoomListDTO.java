package com.xuexiang.templateproject.http.chat.entity;

import com.google.gson.annotations.SerializedName;
import com.xuexiang.templateproject.http.user.entity.UserDTORes;

import java.util.List;

public class ChatRoomListDTO {


    @SerializedName("from")
    private Integer from;
    @SerializedName("pageSize")
    private Integer pageSize;
    @SerializedName("total")
    private Integer total;
    @SerializedName("pages")
    private Integer pages;
    @SerializedName("list")
    private List<ChatRoomItem> list;

    public Integer getFrom() {
        return from;
    }

    public void setFrom(Integer from) {
        this.from = from;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public List<ChatRoomItem> getList() {
        return list;
    }

    public void setList(List<ChatRoomItem> list) {
        this.list = list;
    }

    public static class ChatRoomItem {
        @SerializedName("id")
        private String id;
        @SerializedName("sendUser")
        private String sendUser;
        @SerializedName("receiveUser")
        private String receiveUser;
        @SerializedName("receiveUserInfo")
        private UserDTORes receiveUserInfo;
        @SerializedName("chatRecords")
        private List<ChatRecordsDTO> chatRecords;
        @SerializedName("noReadMessage")
        private int noReadMessage;
        @SerializedName("delFlg")
        private String delFlg;
        @SerializedName("creator")
        private String creator;
        @SerializedName("createTime")
        private String createTime;
        @SerializedName("lastRecordTimeView")
        private String lastRecordTimeView;
        @SerializedName("updater")
        private String updater;
        @SerializedName("updateTime")
        private String updateTime;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

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

        public UserDTORes getReceiveUserInfo() {
            return receiveUserInfo;
        }

        public void setReceiveUserInfo(UserDTORes receiveUserInfo) {
            this.receiveUserInfo = receiveUserInfo;
        }

        public List<ChatRecordsDTO> getChatRecords() {
            return chatRecords;
        }

        public void setChatRecords(List<ChatRecordsDTO> chatRecords) {
            this.chatRecords = chatRecords;
        }

        public String getDelFlg() {
            return delFlg;
        }

        public void setDelFlg(String delFlg) {
            this.delFlg = delFlg;
        }

        public String getCreator() {
            return creator;
        }

        public void setCreator(String creator) {
            this.creator = creator;
        }


        public String getLastRecordTimeView() {
            return lastRecordTimeView;
        }

        public void setLastRecordTimeView(String lastRecordTimeView) {
            this.lastRecordTimeView = lastRecordTimeView;
        }

        public int getNoReadMessage() {
            return noReadMessage;
        }

        public void setNoReadMessage(int noReadMessage) {
            this.noReadMessage = noReadMessage;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getUpdater() {
            return updater;
        }

        public void setUpdater(String updater) {
            this.updater = updater;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }


        public static class ChatRecordsDTO {
            @SerializedName("id")
            private String id;
            @SerializedName("chatRoomId")
            private String chatRoomId;
            @SerializedName("sendId")
            private String sendId;
            @SerializedName("receiveId")
            private String receiveId;
            @SerializedName("msgContent")
            private String msgContent;
            @SerializedName("readStatus")
            private String readStatus;
            @SerializedName("msgType")
            private String msgType;
            @SerializedName("delFlg")
            private String delFlg;
            @SerializedName("creator")
            private String creator;
            @SerializedName("createTime")
            private String createTime;
            @SerializedName("updater")
            private String updater;
            @SerializedName("updateTime")
            private String updateTime;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getChatRoomId() {
                return chatRoomId;
            }

            public void setChatRoomId(String chatRoomId) {
                this.chatRoomId = chatRoomId;
            }

            public String getSendId() {
                return sendId;
            }

            public void setSendId(String sendId) {
                this.sendId = sendId;
            }

            public String getReceiveId() {
                return receiveId;
            }

            public void setReceiveId(String receiveId) {
                this.receiveId = receiveId;
            }

            public String getMsgContent() {
                return msgContent;
            }

            public void setMsgContent(String msgContent) {
                this.msgContent = msgContent;
            }

            public String getReadStatus() {
                return readStatus;
            }

            public void setReadStatus(String readStatus) {
                this.readStatus = readStatus;
            }

            public String getMsgType() {
                return msgType;
            }

            public void setMsgType(String msgType) {
                this.msgType = msgType;
            }

            public String getDelFlg() {
                return delFlg;
            }

            public void setDelFlg(String delFlg) {
                this.delFlg = delFlg;
            }

            public String getCreator() {
                return creator;
            }

            public void setCreator(String creator) {
                this.creator = creator;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getUpdater() {
                return updater;
            }

            public void setUpdater(String updater) {
                this.updater = updater;
            }

            public String getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(String updateTime) {
                this.updateTime = updateTime;
            }
        }
    }
}
