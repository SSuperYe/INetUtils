package com.dangdailife.inetutils.base;

import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.dangdailife.inetutils.R;

import butterknife.BindView;

/**
 * @author Mr.Ye
 * @description 带toolbar的activity基类
 * @datetime 2017/08/02 14:01
 * @email superrhye@163.com
 */

public abstract class BaseToolBarActivity extends BaseActivity {

    /**
     * toolbar后退键是否可见，默认开启
     */
    protected boolean isToolBarBackVisible = true;


    @BindView(R.id.toolbar)
    protected Toolbar toolbar;
    @BindView(R.id.title_name)
    protected TextView titleName;

    @Override
    protected void initBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("");
        initToolbarTitle();
        if (isToolBarBackVisible)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /**
     * 对Title进行初始化
     */
    public abstract void initToolbarTitle();
}
