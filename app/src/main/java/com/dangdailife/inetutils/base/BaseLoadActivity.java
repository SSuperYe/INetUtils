package com.dangdailife.inetutils.base;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.dangdailife.inetutils.R;

/**
 * @author Mr.Ye
 * @description 不实用dialog做加载框时，使用
 * @datetime 2018/01/22 15:59
 * @email superrhye@163.com
 */

public abstract class BaseLoadActivity extends BaseEmptyViewActivity {

    protected View loadView;

    @Override
    protected void initView() {
        super.initView();
        loadView = View.inflate(mActivity, R.layout.progress_layout, null);
        loadView.setVisibility(View.GONE);
        rootView.addView(loadView, 1, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }
}
