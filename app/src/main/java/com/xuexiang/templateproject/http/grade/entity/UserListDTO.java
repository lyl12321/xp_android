package com.xuexiang.templateproject.http.grade.entity;

import com.google.gson.annotations.SerializedName;
import com.xuexiang.templateproject.http.user.entity.UserDTORes;

import java.util.List;

public class UserListDTO {


    @SerializedName("from")
    private Integer from;
    @SerializedName("pageSize")
    private Integer pageSize;
    @SerializedName("total")
    private Integer total;
    @SerializedName("pages")
    private Integer pages;
    @SerializedName("list")
    private List<UserDTORes> list;

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

    public List<UserDTORes> getList() {
        return list;
    }

    public void setList(List<UserDTORes> list) {
        this.list = list;
    }
}
