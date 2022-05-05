package com.xuexiang.templateproject.http.check.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CheckListDTO {


    @SerializedName("from")
    private Integer from;
    @SerializedName("pageSize")
    private Integer pageSize;
    @SerializedName("total")
    private Integer total;
    @SerializedName("pages")
    private Integer pages;
    @SerializedName("list")
    private List<CheckListItem> list;

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

    public List<CheckListItem> getList() {
        return list;
    }

    public void setList(List<CheckListItem> list) {
        this.list = list;
    }

    public static class CheckListItem {
        @SerializedName("id")
        private String id;
        @SerializedName("checkTitle")
        private String checkTitle;
        @SerializedName("checkDesc")
        private String checkDesc;
        @SerializedName("checkPublisher")
        private String checkPublisher;
        @SerializedName("checkStartTime")
        private String checkStartTime;
        @SerializedName("checkEndTime")
        private String checkEndTime;
        @SerializedName("receive")
        private String receive;
        @SerializedName("status")
        private String status;
        @SerializedName("userCheckStatus")
        private String userCheckStatus;
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

        public String getCheckTitle() {
            return checkTitle;
        }

        public void setCheckTitle(String checkTitle) {
            this.checkTitle = checkTitle;
        }

        public String getCheckDesc() {
            return checkDesc;
        }

        public void setCheckDesc(String checkDesc) {
            this.checkDesc = checkDesc;
        }

        public String getCheckPublisher() {
            return checkPublisher;
        }

        public void setCheckPublisher(String checkPublisher) {
            this.checkPublisher = checkPublisher;
        }

        public String getCheckStartTime() {
            return checkStartTime;
        }

        public void setCheckStartTime(String checkStartTime) {
            this.checkStartTime = checkStartTime;
        }

        public String getCheckEndTime() {
            return checkEndTime;
        }

        public void setCheckEndTime(String checkEndTime) {
            this.checkEndTime = checkEndTime;
        }

        public String getReceive() {
            return receive;
        }

        public void setReceive(String receive) {
            this.receive = receive;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getUserCheckStatus() {
            return userCheckStatus;
        }

        public void setUserCheckStatus(String userCheckStatus) {
            this.userCheckStatus = userCheckStatus;
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
