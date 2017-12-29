package com.dangdailife.networks.network;

import android.content.Context;
import android.text.TextUtils;

import com.dangdailife.networks.network.utils.LogUtil;
import com.dangdailife.networks.R;
import com.dangdailife.networks.network.utils.CheckNetUtils;
import com.google.gson.Gson;
import com.google.gson.stream.MalformedJsonException;

import java.net.ProtocolException;
import java.net.SocketTimeoutException;
import java.util.Iterator;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;

/**
 * @author Mr.Ye
 * @description 错误处理类，基本用于处理http状态码变更出200-300之外时，rx无法快速的获取error内容
 * @datetime 2017/08/16 15:41
 * @email superrhye@163.com
 */

public class HttpExceptionHandler {
    public static String handlerException(Throwable e, final Context context) {
        String tip = "";
        if (e instanceof HttpException) {
            Response response = ((HttpException) e).response();
            ResponseBody body = response.errorBody();
            try {
                String json = null;
                if (body != null) {
                    json = body.string();
                }
                //针对各自的对象更改即可
                HttpError httpError = new Gson().fromJson(json, HttpError.class);
                if (httpError.errors != null) {
                    //第一种取法
                    Iterator<String> iterator = httpError.errors.keySet().iterator();
                    while (iterator.hasNext()) {
                        String key = iterator.next();
                        List<String> values = (List<String>) httpError.errors.get(key);
                        tip = values.get(0);
                        LogUtil.e("------errors", tip);
                        return tip;
                    }
                    //第二种取法,非常规取法，可自行对字符串截取
                    int start = httpError.errors.toString().indexOf("[") + 1;//左闭右开，+1
                    int end = httpError.errors.toString().indexOf("]");
                    tip = httpError.errors.toString().substring(start, end);
                } else {
                    tip = httpError.getMessage();
                    LogUtil.e("------error msg", tip);
                }
            } catch (Exception e1) {
                LogUtil.e("---------", e1.getMessage());
            }
        } else if (e instanceof ProtocolException) {//重定向异常
            return context.getString(R.string.error_tip);
        } else if (!CheckNetUtils.isNetworkConnected(context)) {
            return context.getString(R.string.no_net_tip);
        } else if (e instanceof SocketTimeoutException) {
            return context.getString(R.string.timeout);
        } else if (e instanceof MalformedJsonException) {
            return context.getString(R.string.error_tip);
        }
        return tip = TextUtils.isEmpty(tip) ? context.getString(R.string.error_tip) : tip;
    }
}
