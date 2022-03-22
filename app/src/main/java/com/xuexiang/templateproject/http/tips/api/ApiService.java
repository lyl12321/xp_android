

package com.xuexiang.templateproject.http.tips.api;

import com.xuexiang.templateproject.http.tips.entity.TipInfo;
import com.xuexiang.xhttp2.model.ApiResult;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**

 * @since 2021/1/9 7:01 PM
 */
public class ApiService {

    /**
     * 使用的是retrofit的接口定义
     */
    public interface IGetService {

        /**
         * 获得小贴士
         */
        @GET("/xuexiangjys/Resource/raw/master/jsonapi/tips.json")
        Observable<ApiResult<List<TipInfo>>> getTips();
    }

}
