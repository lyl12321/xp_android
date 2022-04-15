package com.xuexiang.templateproject.http.wallet.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WalletListDTO {


    @SerializedName("from")
    private Integer from;
    @SerializedName("pageSize")
    private Integer pageSize;
    @SerializedName("total")
    private Integer total;
    @SerializedName("pages")
    private Integer pages;
    @SerializedName("list")
    private List<WalletDTO> list;

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

    public List<WalletDTO> getList() {
        return list;
    }

    public void setList(List<WalletDTO> list) {
        this.list = list;
    }

    public static class WalletDTO {
        @SerializedName("id")
        private String id;
        @SerializedName("userId")
        private String userId;
        @SerializedName("balance")
        private Integer balance;
        @SerializedName("tradeType")
        private String tradeType;
        @SerializedName("tradeUser")
        private String tradeUser;
        @SerializedName("changeAmount")
        private Integer changeAmount;
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

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public Integer getBalance() {
            return balance;
        }

        public void setBalance(Integer balance) {
            this.balance = balance;
        }

        public String getTradeType() {
            return tradeType;
        }

        public void setTradeType(String tradeType) {
            this.tradeType = tradeType;
        }

        public String getTradeUser() {
            return tradeUser;
        }

        public void setTradeUser(String tradeUser) {
            this.tradeUser = tradeUser;
        }

        public Integer getChangeAmount() {
            return changeAmount;
        }

        public void setChangeAmount(Integer changeAmount) {
            this.changeAmount = changeAmount;
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
