package com.dangdailife.networks.network.subscriber;

import android.app.Activity;
import android.widget.Toast;

import zlc.season.rxdownload.DownloadStatus;

/**
 * @author Mr.Ye
 * @description 文件下载接收器
 * @datetime 2017/07/17 16:49
 * @email superrhye@163.com
 */

public abstract class FileSubscriber extends MySubscriber<DownloadStatus> {

    private static final String TAG = "FileSubscriber";

    /**
     * 目标文件存储的文件夹路径
     */
    protected String destFileDir;
    /**
     * 目标文件存储的文件名
     */
    protected String destFileName;

    protected String filePath = destFileDir + "/" + destFileName;

    public FileSubscriber(Activity activity, String destFileDir, String destFileName) {
        super(activity);
        this.destFileDir = destFileDir;
        this.destFileName = destFileName;
    }

    public abstract void inProgress(int progress, long total);

    @Override
    public void onSuccess(DownloadStatus downloadStatus) {
        try {
            inProgress(Integer.parseInt(downloadStatus.getPercent().split("\\.")[0]), downloadStatus.getTotalSize());
        } catch (Exception e) {
            Toast.makeText(mActivity.getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFailure(String message) {
        Toast.makeText(mActivity.getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}
