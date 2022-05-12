package com.xuexiang.templateproject.http.chat.entity;

import com.google.gson.annotations.SerializedName;

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
        private ReceiveUserInfoDTO receiveUserInfo;
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

        public ReceiveUserInfoDTO getReceiveUserInfo() {
            return receiveUserInfo;
        }

        public void setReceiveUserInfo(ReceiveUserInfoDTO receiveUserInfo) {
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

        public static class ReceiveUserInfoDTO {
            @SerializedName("id")
            private String id;
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
            @SerializedName("username")
            private String username;
            @SerializedName("password")
            private String password;
            @SerializedName("phone")
            private String phone;
            @SerializedName("userType")
            private String userType;
            @SerializedName("classCode")
            private String classCode;
            @SerializedName("userStatus")
            private String userStatus;
            @SerializedName("errorTime")
            private String errorTime;
            @SerializedName("locked")
            private String locked;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
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

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getPassword() {
                return password;
            }

            public void setPassword(String password) {
                this.password = password;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getUserType() {
                return userType;
            }

            public void setUserType(String userType) {
                this.userType = userType;
            }

            public String getClassCode() {
                return classCode;
            }

            public void setClassCode(String classCode) {
                this.classCode = classCode;
            }

            public String getUserStatus() {
                return userStatus;
            }

            public void setUserStatus(String userStatus) {
                this.userStatus = userStatus;
            }

            public String getErrorTime() {
                return errorTime;
            }

            public void setErrorTime(String errorTime) {
                this.errorTime = errorTime;
            }

            public String getLocked() {
                return locked;
            }

            public void setLocked(String locked) {
                this.locked = locked;
            }


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
