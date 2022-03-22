package com.xuexiang.templateproject.http.user.api;

import com.xuexiang.templateproject.http.tips.entity.TipInfo;
import com.xuexiang.xhttp2.model.ApiResult;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public class UserApiService {

    public interface UserService {

        /**
         * 获得小贴士
         */
        @GET("saveOrUpdate")
        Observable<ApiResult<List<TipInfo>>> saveOrUpdate();
    }
}
