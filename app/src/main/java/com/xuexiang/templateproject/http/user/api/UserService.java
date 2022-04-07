package com.xuexiang.templateproject.http.user.api;

import com.xuexiang.templateproject.http.user.entity.User;
import com.xuexiang.xhttp2.model.ApiResult;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserService {

    /**
     * 用户注册
     */
    @POST("/user/insert")
    Observable<ApiResult<String>> register(@Body User user);
}
