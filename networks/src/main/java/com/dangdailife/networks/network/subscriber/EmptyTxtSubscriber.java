package com.dangdailife.networks.network.subscriber;

import android.app.Activity;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author Mr.Ye
 * @description 单挑查询（各种异常情况需要空界面展示）
 * @datetime 2018/01/04 10:57
 * @email superrhye@163.com
 */

public abstract class EmptyTxtSubscriber<T> extends MySubscriber<T> {

    public EmptyTxtSubscriber(Activity activity, TextView tvEmptyHint) {
        super(activity, tvEmptyHint, true);
    }

    @Override
    protected void onSuccess(T t) {

    }

    @Override
    protected void onFailure(String message) {
        Toast.makeText(mActivity, message, Toast.LENGTH_SHORT).show();
    }
}
