package com.dangdailife.networks.network.utils;


import android.util.Log;

import com.dangdailife.networks.BuildConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * @author Joy Wong
 * @description log帮助类，debug模式下输出日志
 * @datetime 2016/06/23 9:31
 * @email wjb18814888154@gmail.com
 */
public class LogUtil {
    private static final boolean DEBUG = BuildConfig.DEBUG;

    public static void v(String tag, String message) {
        if (DEBUG) {
            Log.v(tag, message);
        }
    }

    public static void d(String tag, String message) {
        if (DEBUG) {
            Log.d(tag, message);
        }
    }

    public static void i(String tag, String message) {
        if (DEBUG) {
            Log.i(tag, message);
        }
    }

    public static void w(String tag, String message) {
        if (DEBUG) {
            Log.w(tag, message);
        }
    }

    public static void e(String tag, String message) {
        if (DEBUG) {
            Log.e(tag, message);
        }
    }

    public static void e(String tag, String message, Exception e) {
        if (DEBUG) {
            Log.e(tag, message, e);
        }
    }

    public static String showJson(String jsonStr) {
        StringBuilder message = new StringBuilder();
        if (DEBUG) {
            try {
                if (jsonStr.startsWith("{")) {
                    JSONObject jsonObject = new JSONObject(jsonStr);
                    message.append(jsonObject.toString(4));
                } else if (jsonStr.startsWith("[")) {
                    JSONArray jsonArray = new JSONArray(jsonStr);
                    message.append(jsonArray.toString(4));
                } else {
                    message.append(jsonStr);
                }
            } catch (JSONException e) {
                message.append(e.getMessage());
            }
        }
        return message.toString();
    }
}
