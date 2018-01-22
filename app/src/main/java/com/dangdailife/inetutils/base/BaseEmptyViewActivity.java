package com.dangdailife.inetutils.base;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dangdailife.inetutils.R;

/**
 * @author Mr.Ye
 * @description base中add 空布局
 * @datetime 2018/01/22 11:07
 * @email superrhye@163.com
 */

public abstract class BaseEmptyViewActivity extends BaseToolBarActivity {

    protected TextView emptyTxtView;

    @Override
    protected void initView() {
        super.initView();
        emptyTxtView = (TextView) View.inflate(mActivity, R.layout.empty_view_text, null);
        rootView.addView(emptyTxtView, 1, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }
}
