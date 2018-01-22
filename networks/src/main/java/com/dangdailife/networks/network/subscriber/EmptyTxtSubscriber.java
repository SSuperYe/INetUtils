package com.dangdailife.networks.network.subscriber;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author Mr.Ye
 * @description 单挑查询（各种异常情况需要空界面展示）
 * @datetime 2018/01/04 10:57
 * @email superrhye@163.com
 */

public abstract class EmptyTxtSubscriber<T> extends MySubscriber<T> {

    protected TextView hintTxt;

    public EmptyTxtSubscriber(Activity activity, TextView tvEmptyHint) {
        super(activity);
        this.hintTxt = tvEmptyHint;
    }

    /**
     * 在Rxjava中我们什么时候来显示呢。一开始觉得是放在Subscriber<T>的onStart中。onStart可以用作流程开始前的初始化。
     * 然而 onStart()由于在 subscribe()发生时就被调用了，因此不能指定线程，而是只能执行在 subscribe()被调用时的线程。所以onStart并不能保证永远在主线程运行。
     * 与 onStart()相对应的有一个方法 doOnSubscribe()，它和 onStart()同样是在subscribe()调用后而且在事件发送前执行，但区别在于它可以指定线程。
     * 默认情况下， doOnSubscribe()执行在 subscribe()发生的线程
     */
    public void setHintTxtVisible(){
        if (hintTxt == null) {
            return;
        }
        hintTxt.setVisibility(View.VISIBLE);
        hintTxt.setText(null);
    }

    @Override
    protected void onSuccess(T t) {
        if (hintTxt != null) {
            hintTxt.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onFailure(String message) {
        hintTxt.setVisibility(View.VISIBLE);
        Toast.makeText(mActivity, message, Toast.LENGTH_SHORT).show();
    }
}
