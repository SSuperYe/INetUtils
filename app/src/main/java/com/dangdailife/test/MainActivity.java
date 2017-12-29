package com.dangdailife.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.dangdailife.networks.network.BaseResult;
import com.dangdailife.networks.network.HttpExceptionHandler;
import com.dangdailife.networks.network.NetWorks;
import com.dangdailife.networks.network.subscriber.MySubscriber;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //此处栗子为testGetQuery1()返回的是BaseResult<BaseResult>，直接取到BaseResult里面的泛型BaseResult
        //我们的api返回的数据经常是data里面含有一个对象等等，所以，此方法常用
        NetWorks.setSubscribe(NetWorks.api.testGetQuery1(""), new MySubscriber<BaseResult>() {
            @Override
            protected void onSuccess(BaseResult baseResult) {
                // TODO: 2017/12/28 0028 做一些操作
            }

            @Override
            protected void onFailure(String message) {
                // TODO: 2017/12/28 0028 对错误进行处理，http特殊错误，已经在框架处，处理了，可前往框架的HttpExceptionHandler结合后台进行自定义处理
            }
        });

        //该方法直接获取整个返回对象
        //常用于为了请求得到一个结果的api，比如一个获取成功与否的请求
        NetWorks.setSpecialSubscribe(NetWorks.api.testGetQuery(""), new MySubscriber<BaseResult>() {
            @Override
            protected void onSuccess(BaseResult baseResult) {
                // TODO: 2017/12/28 0028 做一些操作
            }

            @Override
            protected void onFailure(String message) {
                // TODO: 2017/12/28 0028 对错误进行处理，http特殊错误，已经在框架处，处理了，可前往框架的HttpExceptionHandler结合后台进行自定义处理
            }
        });

        //剩余两个用法一致
        //只是没有加载框提示的请求
        //常用于不与用户进行交互的请求
//        NetWorks.setNoProgressSubscribe();
//        NetWorks.setNoProgressSpecialSubscribe();
    }
}
