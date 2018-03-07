package com.dangdailife.inetutils;

import android.app.Application;
import android.os.Build;
import android.util.ArrayMap;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Mr.Ye
 * @description
 * @datetime 2017/08/01 19:54
 * @email superrhye@163.com
 */

public class NetWorkApplication extends Application {

    public static Application mApplication;

    //统一处理4.4以下arraymap不能用的情况
    public static Map<String, Object> map;

    public static Application getInstance() {
        return mApplication;
    }

    /**
     * 为map设置值
     *
     * @param clean 是否清空map,默认false
     * @param key   key
     * @param value value
     */
    public static void setMap(String key, Object value, boolean... clean) {
        if (clean.length == 0) {
            map.put(key, value);
            return;
        }
        if (clean[0]) {
            map.clear();
        }
        map.put(key, value);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mApplication = this;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            map = new ArrayMap<>();
        } else {
            map = new HashMap<>();
        }
    }
}
