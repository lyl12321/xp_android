package com.xuexiang.templateproject.http.check.entity;

import com.google.gson.annotations.SerializedName;

public class CheckDetailsDTO {


    @SerializedName("id")
    private String id;
    @SerializedName("checkPid")
    private String checkPid;
    @SerializedName("checkUser")
    private String checkUser;
    @SerializedName("checkClassCode")
    private String checkClassCode;
    @SerializedName("checkContent")
    private String checkContent;
    @SerializedName("status")
    private String status;
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

    public String getCheckPid() {
        return checkPid;
    }

    public void setCheckPid(String checkPid) {
        this.checkPid = checkPid;
    }

    public String getCheckUser() {
        return checkUser;
    }

    public void setCheckUser(String checkUser) {
        this.checkUser = checkUser;
    }

    public String getCheckClassCode() {
        return checkClassCode;
    }

    public void setCheckClassCode(String checkClassCode) {
        this.checkClassCode = checkClassCode;
    }

    public String getCheckContent() {
        return checkContent;
    }

    public void setCheckContent(String checkContent) {
        this.checkContent = checkContent;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
