package com.xuexiang.templateproject.http.order.entity;

import com.google.gson.annotations.SerializedName;
import com.xuexiang.templateproject.http.goods.entity.GoodsListDTO;

import java.util.List;

public class GoodsOrderListDTO {


    @SerializedName("from")
    private Integer from;
    @SerializedName("pageSize")
    private Integer pageSize;
    @SerializedName("total")
    private Integer total;
    @SerializedName("pages")
    private Integer pages;
    @SerializedName("list")
    private List<ListDTO> list;

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

    public List<ListDTO> getList() {
        return list;
    }

    public void setList(List<ListDTO> list) {
        this.list = list;
    }

    public static class ListDTO {
        @SerializedName("id")
        private String id;
        @SerializedName("userId")
        private String userId;
        @SerializedName("goodsId")
        private String goodsId;
        @SerializedName("delFlg")
        private String delFlg;
        @SerializedName("creator")
        private String creator;
        @SerializedName("createTime")
        private String createTime;
        @SerializedName("updater")
        private Object updater;
        @SerializedName("updateTime")
        private Object updateTime;
        @SerializedName("goodsEntity")
        private GoodsListDTO.GoodsDTO goodsEntity;

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

        public String getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(String goodsId) {
            this.goodsId = goodsId;
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

        public GoodsListDTO.GoodsDTO getGoodsEntity() {
            return goodsEntity;
        }

        public void setGoodsEntity(GoodsListDTO.GoodsDTO goodsEntity) {
            this.goodsEntity = goodsEntity;
        }

        public static class GoodsEntityDTO {
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
            @SerializedName("goodsName")
            private String goodsName;
            @SerializedName("goodsDescribe")
            private String goodsDescribe;
            @SerializedName("goodsPicUrl")
            private String goodsPicUrl;
            @SerializedName("goodsPrice")
            private Integer goodsPrice;
            @SerializedName("classification")
            private String classification;

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

            public String getGoodsName() {
                return goodsName;
            }

            public void setGoodsName(String goodsName) {
                this.goodsName = goodsName;
            }

            public String getGoodsDescribe() {
                return goodsDescribe;
            }

            public void setGoodsDescribe(String goodsDescribe) {
                this.goodsDescribe = goodsDescribe;
            }

            public String getGoodsPicUrl() {
                return goodsPicUrl;
            }

            public void setGoodsPicUrl(String goodsPicUrl) {
                this.goodsPicUrl = goodsPicUrl;
            }

            public Integer getGoodsPrice() {
                return goodsPrice;
            }

            public void setGoodsPrice(Integer goodsPrice) {
                this.goodsPrice = goodsPrice;
            }

            public String getClassification() {
                return classification;
            }

            public void setClassification(String classification) {
                this.classification = classification;
            }
        }
    }
}
