package com.dangdailife.inetutils.base;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.dangdailife.inetutils.R;
import com.dangdailife.networks.network.utils.LogUtil;

import butterknife.ButterKnife;

/**
 * @author Mr.Ye
 * @description activity基类
 * @datetime 2017/07/22 14:33
 * @email superrhye@163.com
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected BaseActivity mActivity;
    protected ViewGroup rootView;
    private static final String TAG = "BaseActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mActivity = this;
        LogUtil.d(TAG, getClass().getSimpleName() + ":onCreate");
        rootView = (ViewGroup) View.inflate(mActivity, getContentViewId(), null);
        setContentView(rootView);
        ButterKnife.bind(this);
        initData();
        initBar();
        initView();
        fetchData();
    }

    protected abstract int getContentViewId();

    protected void initBar() {
        Log.e("--------", "initBar");
    }

    protected void initView() {
        Log.e("--------", "initView");
    }

    /**
     * 从intent获取数据，绑定数据
     */
    protected void initData() {

    }

    /**
     * 从网络获取数据
     */
    protected void fetchData() {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtil.d(TAG, getClass().getSimpleName() + ":onDestroy");
    }


    @Override
    public void onBackPressed() {
        try {
            super.onBackPressed();
            overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
        } catch (Exception e) {
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        LogUtil.d(TAG, getClass().getSimpleName() + ":onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtil.d(TAG, getClass().getSimpleName() + ":onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogUtil.d(TAG, getClass().getSimpleName() + ":onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtil.d(TAG, getClass().getSimpleName() + ":onStop");
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
    }
}
