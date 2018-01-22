package com.dangdailife.inetutils;

import android.view.View;
import android.widget.TextView;

import com.dangdailife.inetutils.base.BaseLoadActivity;
import com.dangdailife.networks.network.BaseResult;
import com.dangdailife.networks.network.NetWorks;
import com.dangdailife.networks.network.subscriber.EmptyTxtSubscriber;

import butterknife.BindView;

/**
 * @author Mr.Ye
 * @description 空布局简单栗子（请求前会有一个空布局覆盖，暂时遮盖布局）
 * @datetime 2018/01/22 10:51
 * @email superrhye@163.com
 */

public class EmptyActivity extends BaseLoadActivity {
    @BindView(R.id.tv_test)
    TextView tvTest;

    @Override
    public void initToolbarTitle() {
        titleName.setText(R.string.empty_test);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_empty;
    }

    @Override
    protected void fetchData() {
        super.fetchData();
        emptyGet();
    }

    /**
     * dialog形式的加载框
     */
    private void emptyGet(){
        NetWorks.setNoProgressSubscribe(NetWorks.api.testGetQuery1("11"), new EmptyTxtSubscriber<BaseResult>(mActivity, emptyTxtView) {

            @Override
            protected void onSuccess(BaseResult baseResult) {
                super.onSuccess(baseResult);
                tvTest.setText("success");
            }

            @Override
            protected void onFailure(String message) {
                super.onFailure(message);
                //这种前往复杂界面的请求，出现错误，一般是需要展示空界面的，没有理由将无数据的页面展示，想改可以重写选择gone
                //显示文案可以自定义
                emptyTxtView.setText(message);
            }
        });
    }

    /**
     * progressBar形式的加载框，不需要处理按返回dialog的消失的情景
     */
    private void loadGet(){
        NetWorks.setNoProgressSubscribe(NetWorks.api.testGetQuery1("11"), new EmptyTxtSubscriber<BaseResult>(mActivity, emptyTxtView) {
            @Override
            public void onStart() {
                super.onStart();
                loadView.setVisibility(View.VISIBLE);
            }

            @Override
            protected void onSuccess(BaseResult baseResult) {
                super.onSuccess(baseResult);
                tvTest.setText("success");
            }

            @Override
            protected void onFailure(String message) {
                super.onFailure(message);
                //这种前往复杂界面的请求，出现错误，一般是需要展示空界面的，没有理由将无数据的页面展示，想改可以重写选择gone
                //显示文案可以自定义
                emptyTxtView.setText(message);
                loadView.setVisibility(View.GONE);
            }

            @Override
            public void onCompleted() {
                super.onCompleted();
                loadView.setVisibility(View.GONE);
            }
        });
    }
}
