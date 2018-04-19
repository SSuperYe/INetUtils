package com.dangdailife.inetutils.api;


import com.dangdailife.networks.network.BaseResult;

import java.util.Map;

import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * @author Mr.Ye
 * @description api
 * @datetime 2017/07/17 16:57
 * @email superrhye@163.com
 */
public interface ApiService {

    /**
     * 简单介绍，详细了解请查阅retrofit注解
     */

    /**
     * 这个是post表单验证
     *
     * @FormUrlEncoded 表单验证
     * @Header 请求头
     * @Field 就是post提交的参数
     * @FieldMap 就是post提交的参数组合成一个map传入（key-value形式）
     * @Body 就是post提交的对象
     */
    @FormUrlEncoded
    @POST("your api")
    Observable<BaseResult> testPostFieldWithHeader(@Header("your header") String header, @Field("your param") String param);

    @FormUrlEncoded
    @POST("your api")
    Observable<BaseResult> testPostFieldMap(@FieldMap Map<String, Object> map);


    /**
     * @Body 可以直接提交对象
     */
    @POST("your api")
    Observable<BaseResult> testPostFieldMap(@Body Object o);


    /**
     * 这个是get请求，无需表单验证
     *
     * @QueryMap 就是get提交的参数组合成一个map传入（key-value形式）
     * @Query 就是get提交的参数
     */
    @GET("your api")
    Observable<BaseResult> testGetQueryMap(@Header("your header") String header, @QueryMap Map<String, Object> map);

    @GET("your api")
    Observable<BaseResult> testGetQuery(@Query("your param") String param);

    @GET("your api")
    Observable<BaseResult<BaseResult>> testGetQuery1(@Query("your param") String param);

    /**
     * path可以结合使用，不分请求
     *
     * @Path 某些api直接是带参数的（get，post随意），举个栗子如xxx/xxx/12，那么api为xxx/xxx/{id}
     */
    @POST("your api")
    //@GET("your api")
    Observable<BaseResult> testPostPath(@Path("id") int yourPath);
}
