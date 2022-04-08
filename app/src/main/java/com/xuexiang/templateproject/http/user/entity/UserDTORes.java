package com.xuexiang.templateproject.http.user.entity;

import com.google.gson.annotations.SerializedName;

public class UserDTORes {
    @SerializedName("id")
    private String id;
    @SerializedName("delFlg")
    private Object delFlg;
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
    private Object userType;
    @SerializedName("classCode")
    private String classCode;
    @SerializedName("userStatus")
    private Object userStatus;
    @SerializedName("errorTime")
    private Object errorTime;
    @SerializedName("locked")
    private Object locked;
    @SerializedName("delFlag")
    private String delFlag;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Object getDelFlg() {
        return delFlg;
    }

    public void setDelFlg(Object delFlg) {
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

    public Object getUserType() {
        return userType;
    }

    public void setUserType(Object userType) {
        this.userType = userType;
    }

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public Object getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Object userStatus) {
        this.userStatus = userStatus;
    }

    public Object getErrorTime() {
        return errorTime;
    }

    public void setErrorTime(Object errorTime) {
        this.errorTime = errorTime;
    }

    public Object getLocked() {
        return locked;
    }

    public void setLocked(Object locked) {
        this.locked = locked;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }
}
