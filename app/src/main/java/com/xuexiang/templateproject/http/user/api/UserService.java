package com.xuexiang.templateproject.http.user.api;

import com.xuexiang.xhttp2.model.ApiResult;

import io.reactivex.Observable;
import retrofit2.http.POST;

public interface UserService {

    /**
     * 获得小贴士
     */
    @POST("/user/register")
    Observable<ApiResult<String>> register();
}
