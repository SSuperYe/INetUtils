package com.dangdailife.networks.network.subscriber;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dangdailife.networks.R;
import com.dangdailife.networks.network.HttpExceptionHandler;
import com.dangdailife.networks.network.progress.ProgressCancelListener;
import com.dangdailife.networks.network.progress.SimpleLoadDialog;
import com.dangdailife.networks.network.utils.LogUtil;

import java.io.PrintWriter;
import java.io.StringWriter;

import rx.Subscriber;

/**
 * @author Mr.Ye
 * @description 用于在Http请求开始时，自动显示一个ProgressDialog
 * 在Http请求结束是，关闭ProgressDialog
 * 调用者自己对请求数据进行处理
 * @datetime 2017/07/18 11:11
 * @email superrhye@163.com
 */
public abstract class MySubscriber<T> extends Subscriber<T> implements ProgressCancelListener {

    protected Activity mActivity;

    private SimpleLoadDialog dialog;

    protected TextView hintTxt;

    public MySubscriber(Activity activity) {
        this(activity, null, false);
    }

    public MySubscriber(Activity activity, TextView tvEmptyHint) {
        this(activity, tvEmptyHint, false);
    }

    public MySubscriber(Activity activity, TextView tvEmptyHint, boolean isShowEmptyTxt) {
        this.mActivity = activity;
        if (isShowEmptyTxt) {
            this.hintTxt = tvEmptyHint;
        }
        dialog = new SimpleLoadDialog(activity, this, true);
    }

    public MySubscriber() {
    }

    /**
     * 显示Dialog
     */
    public void showProgressDialog() {
        if (dialog != null) {
            dialog.obtainMessage(SimpleLoadDialog.SHOW_PROGRESS_DIALOG).sendToTarget();
        }
    }

    /**
     * 隐藏Dialog
     */
    private void dismissProgressDialog() {
        if (dialog != null) {
            dialog.obtainMessage(SimpleLoadDialog.DISMISS_PROGRESS_DIALOG).sendToTarget();
            dialog = null;
        }
    }

    @Override
    public void onCompleted() {
        dismissProgressDialog();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (hintTxt == null) {
            return;
        }
        hintTxt.setVisibility(View.VISIBLE);
        hintTxt.setText(null);
    }

    @Override
    public void onNext(T t) {
        if (t != null) {
            if (hintTxt != null) {
                hintTxt.setVisibility(View.GONE);
            }
            onSuccess(t);
        } else {
            Toast.makeText(mActivity.getApplicationContext(), mActivity.getString(R.string.data_error), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onError(Throwable e) {
        try {
            StringWriter stringWriter = new StringWriter();
            e.printStackTrace(new PrintWriter(stringWriter));
            Log.e("---------onError", stringWriter.toString());

            String msg = HttpExceptionHandler.handlerException(e, mActivity);
            if (!TextUtils.isEmpty(msg))
                onFailure(msg);
            dismissProgressDialog();
        } catch (Exception e1) {
            LogUtil.e("---------onError", e1.getMessage());
        }
    }


    /**
     * dialog消失后解绑(被取消,如按了返回键)
     */
    @Override
    public void onCancelProgress() {
        mActivity.finish();
        if (!this.isUnsubscribed()) {
            this.unsubscribe();
        }
    }

    protected abstract void onSuccess(T t);

    protected abstract void onFailure(String message);

}
