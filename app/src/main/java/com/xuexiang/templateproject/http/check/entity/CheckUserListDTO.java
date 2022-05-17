package com.xuexiang.templateproject.http.check.entity;

import com.google.gson.annotations.SerializedName;
import com.xuexiang.templateproject.http.user.entity.UserDTORes;

import java.util.List;

public class CheckUserListDTO {


    @SerializedName("from")
    private Integer from;
    @SerializedName("pageSize")
    private Integer pageSize;
    @SerializedName("total")
    private Integer total;
    @SerializedName("pages")
    private Integer pages;
    @SerializedName("list")
    private List<CheckUserItem> list;

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

    public List<CheckUserItem> getList() {
        return list;
    }

    public void setList(List<CheckUserItem> list) {
        this.list = list;
    }

    public static class CheckUserItem {
        @SerializedName("id")
        private String id;
        @SerializedName("checkPid")
        private String checkPid;
        @SerializedName("checkUser")
        private String checkUser;
        @SerializedName("checkUserInfo")
        private UserDTORes checkUserInfo;
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

        public UserDTORes getCheckUserInfo() {
            return checkUserInfo;
        }

        public void setCheckUserInfo(UserDTORes checkUserInfo) {
            this.checkUserInfo = checkUserInfo;
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

        public static class CheckUserInfoDTO {
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
    }
}
