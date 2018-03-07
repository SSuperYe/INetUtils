package com.dangdailife.networks.network.cookie;

import android.content.Context;

import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 * @author Mr.Ye
 * @description
 * @datetime 2017/12/28 17:10
 * @email superrhye@163.com
 */
public class CookieManager implements CookieJar {

    private final PersistentCookieStore cookieStore;

    public CookieManager(Context context) {
        cookieStore = new PersistentCookieStore(context);
    }

    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        if (cookies != null && !cookies.isEmpty()) {
            for (Cookie item : cookies) {
                cookieStore.add(url, item);
            }
        }
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        return cookieStore.get(url);
    }
}
