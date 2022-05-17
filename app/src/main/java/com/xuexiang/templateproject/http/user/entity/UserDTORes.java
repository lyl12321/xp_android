package com.xuexiang.templateproject.http.user.entity;

import com.google.gson.annotations.SerializedName;

public class UserDTORes {
    @SerializedName("id")
    private String id;
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
    @SerializedName("avatarUrl")
    private String avatarUrl;
    @SerializedName("userStatus")
    private String userStatus;
    @SerializedName("errorTime")
    private Object errorTime;
    @SerializedName("locked")
    private Object locked;
    @SerializedName("delFlag")
    private Object delFlag;
    @SerializedName("creator")
    private String creator;
    @SerializedName("createTime")
    private String createTime;
    @SerializedName("updater")
    private Object updater;
    @SerializedName("updateTime")
    private Object updateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
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

    public Object getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Object delFlag) {
        this.delFlag = delFlag;
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

    public Object getUpdater() {
        return updater;
    }

    public void setUpdater(Object updater) {
        this.updater = updater;
    }

    public Object getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Object updateTime) {
        this.updateTime = updateTime;
    }
}
