package com.ykun.live_library.config;


import android.support.annotation.NonNull;

import java.io.Serializable;

/**
 * 默认前台服务样式
 */
public class ForegroundNotification implements Serializable {
    private String title;
    private String description;
    private int iconRes;
    private ForegroundNotificationClickListener foregroundNotificationClickListener;
    private ForegroundNotification(){
    }
    public ForegroundNotification(ForegroundNotificationClickListener foregroundNotificationClickListener) {
        this.foregroundNotificationClickListener = foregroundNotificationClickListener;
    }


    /**
     * 初始化
     * @return ForegroundNotification
     */
    public static ForegroundNotification ini(){
        return new ForegroundNotification();
    }
    /**
     * 设置前台通知点击事件
     * @param foregroundNotificationClickListener 前台通知点击回调
     * @return ForegroundNotification
     */
    public ForegroundNotification foregroundNotificationClickListener(@NonNull ForegroundNotificationClickListener foregroundNotificationClickListener){
        this.foregroundNotificationClickListener = foregroundNotificationClickListener;
        return this;
    }


    public ForegroundNotificationClickListener getForegroundNotificationClickListener() {
        return foregroundNotificationClickListener;
    }
}
