package com.dangdailife.networks.network;

import com.google.gson.annotations.SerializedName;

/**
 * @author Mr.Ye
 * @description 返回result基类
 * @datetime 2017/07/17 14:20
 * @email superrhye@163.com
 */

public class BaseResult<T> {
    @SerializedName("message")//根据各自的后台决定
    public String message;
    @SerializedName("status_code")//根据各自的后台决定
    public String statusCode;
    @SerializedName("data")
    public T data;
}
