package com.dangdailife.networks.network;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

/**
 * @author Mr.Ye
 * @description
 * @datetime 2017/08/24 12:25
 * @email superrhye@163.com
 */

public class HttpError<T> {

    /**
     * message : xxxx
     * errors : {"key":["value"]}
     * status_code : 422
     */

    @SerializedName("message")
    private String message;
    @SerializedName("errors")
    public Map<String, List<String>> errors;
    @SerializedName("status_code")
    private int statusCode;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

}