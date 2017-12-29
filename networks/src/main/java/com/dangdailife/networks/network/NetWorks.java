package com.dangdailife.networks.network;

import android.util.Log;

import com.dangdailife.networks.BuildConfig;
import com.dangdailife.networks.network.api.ApiService;
import com.dangdailife.networks.network.cookie.CookieManager;
import com.dangdailife.networks.network.factory.CustomGsonConverterFactory;
import com.dangdailife.networks.network.subscriber.FileSubscriber;
import com.dangdailife.networks.network.subscriber.MySubscriber;
import com.dangdailife.networks.network.utils.LogUtil;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import zlc.season.rxdownload.RxDownload;

/**
 * @author Mr.Ye
 * @description
 * @datetime 2017/07/17 16:49
 * @email superrhye@163.com
 */
public class NetWorks {

    private NetWorks() {
    }

    /**
     * 操作符
     * flatmap：一对多转化，可以用做处理需要的数据类型，如可以处理A的数据集中的类B，也可以用做嵌套网络请求，将响应分为多级,对单条数据流起作用
     * map只能分一次，且是一对一转化
     * compose：对一整个请求过程起作用
     * <p>
     * onErrorReturn:让Observable遇到错误时发射一个特殊的项并且正常终止。
     * onErrorResumeNext:让Observable在遇到错误时开始发射第二个Observable的数据序列。
     * onExceptionResumeNext:让Observable在遇到错误时继续发射后面的数据项。
     */

    private static OkHttpClient mOkHttpClient;

    /**
     * api服务
     */
    public static ApiService api;

    // TODO: 2017/8/1 0001 网络协议
    private static final String TAG = "NetWorks";
    public static final String BASE_WEB_URL = BuildConfig.DEBUG ? "" : "";
    public static final String BASE_URL = BASE_WEB_URL + "api/";

    private static final String MEDIA_TYPE_JSON = "application/json; charset=utf-8";
    private static final String HEAD_ACCEPT = "Accept";

    /**
     * 初始化请求框架
     */
    static {
        if (null == api) {
            synchronized (NetWorks.class) {
                if (null == mOkHttpClient) {
                    mOkHttpClient = new OkHttpClient.Builder()
                            .addInterceptor(new NetWorkInterceptor())
                            .cookieJar(new CookieManager())
                            .connectTimeout(5, TimeUnit.SECONDS)
                            .build();
                }
                api = new Retrofit.Builder()
                        //设置服务器路径
                        .baseUrl(BASE_URL)
                        //添加转化库，默认是Gson
//                    .addConverterFactory(GsonConverterFactory.create())
                        .addConverterFactory(CustomGsonConverterFactory.create())
                        //添加回调库，采用RxJava
                        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                        //设置使用okhttp网络请求
                        .client(mOkHttpClient)
                        .build().create(ApiService.class);
            }
        }
    }

    /**
     * 注册接收器 有内泛型Ob
     *
     * @param observable 发射器
     * @param subscriber 接收器
     * @param <U>        Base里的关键data
     * @param <T>        api返回的数据类型
     */
    public static <U, T extends BaseResult<U>> void setSubscribe(Observable<T> observable, final MySubscriber<U> subscriber) {
        observable.subscribeOn(Schedulers.io())//I/O 操作（读写文件、读写数据库、网络信息交互等）
                .observeOn(AndroidSchedulers.mainThread())//回调到主线程
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        subscriber.showProgressDialog();
                    }
                })
                .map(new Func1<T, U>() {
                    @Override
                    public U call(T t) {
                        return t.data;
                    }
                })
                .subscribe(subscriber);
    }

    /**
     * 注册接收器 无内泛型Ob
     *
     * @param observable 发射器
     * @param subscriber 接收器
     * @param <T>        api返回的数据类型
     */
    public static <T> void setSpecialSubscribe(Observable<T> observable, final MySubscriber<T> subscriber) {
        observable.subscribeOn(Schedulers.io())//I/O 操作（读写文件、读写数据库、网络信息交互等）
                .observeOn(AndroidSchedulers.mainThread())//回调到主线程
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        subscriber.showProgressDialog();
                    }
                })
                .subscribe(subscriber);
    }

    /**
     * 注册接收器 无内泛型Ob，没有progress
     *
     * @param observable 发射器
     * @param subscriber 接收器
     * @param <T>        api返回的数据类型
     */
    public static <T> void setNoProgressSpecialSubscribe(Observable<T> observable, final MySubscriber<T> subscriber) {
        observable.subscribeOn(Schedulers.io())//I/O 操作（读写文件、读写数据库、网络信息交互等）
                .observeOn(AndroidSchedulers.mainThread())//回调到主线程
                .subscribe(subscriber);
    }

    /**
     * 注册接收器,没有progress
     *
     * @param observable 发射器
     * @param subscriber 接收器
     * @param <U>        Base里的关键data
     * @param <T>        api返回的数据类型
     */
    public static <U, T extends BaseResult<U>> void setNoProgressSubscribe(Observable<T> observable, final Subscriber<U> subscriber) {
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<T, U>() {
                    @Override
                    public U call(T t) {
                        return t.data;
                    }
                })
                .subscribe(subscriber);
    }


    /**
     * 下载
     *
     * @param fullurl        完整的资源链接
     * @param fileName       保存的文件名
     * @param fileDir        保存的文件夹路径
     * @param fileSubscriber 文件接收器
     * @return
     */
    public static Subscription startDownLoadFile(String fullurl, String fileName, String fileDir, FileSubscriber fileSubscriber) {
        return RxDownload.getInstance()
                .download(fullurl, fileName, fileDir)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(fileSubscriber);
    }

    /**
     * 请求拦截器
     */
    private static class NetWorkInterceptor implements Interceptor {

        private static final String TAG = "NetWorkInterceptor";

        @Override
        public Response intercept(Chain chain) throws IOException {

            Request request = chain.request();
            Request newRequest = request;

            Log.e(TAG, "intercept: " + request.url().url().getPath());
            //判断是否为游客模式，不是请带上token，有些加在头部，有些加在body
//      加在body
//      HttpUrl.Builder builder = request.url().newBuilder();
//      builder.addEncodedPathSegment()

//      加在头部
//      Request.Builder builder = request.newBuilder();
//      builder.addHeader()

//      重新build一个请求发出
//      newRequest = builder.build();
            Request.Builder builder = request.newBuilder();
            builder.addHeader(HEAD_ACCEPT, MEDIA_TYPE_JSON);

            newRequest = builder.build();
            for (String params : newRequest.url().queryParameterNames()) {
                LogUtil.e("------------", "请求参数:" + params + "   ---------   " + newRequest.url().queryParameter(params));
            }

            return chain.proceed(newRequest);
        }
    }
}
