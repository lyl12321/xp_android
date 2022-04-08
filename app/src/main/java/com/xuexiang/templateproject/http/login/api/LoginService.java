package com.xuexiang.templateproject.http.login.api;

import com.xuexiang.xhttp2.model.ApiResult;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

public interface LoginService {

    @POST("/login")
    Observable<ApiResult<String>> login(@QueryMap Map<String,Object> params);
}
