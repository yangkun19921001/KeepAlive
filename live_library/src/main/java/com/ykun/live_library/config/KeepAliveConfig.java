package com.ykun.live_library.config;

import com.ykun.live_library.R;

/**
 * 进程保活需要配置的属性
 */
public class KeepAliveConfig {

    /**
     * Job 的时间
     */
    public static final int JOB_TIME = 1000;
    public static final int FOREGROUD_NOTIFICATION_ID = 8888;
    public static ForegroundNotification foregroundNotification = null;
    public static KeepLiveService keepLiveService = null;
    public static int runMode = RunMode.getShape();

    /**
     * 广播通知的 action
     */
    public static String NOTIFICATION_ACTION = "NOTIFICATION_ACTION";

    public static String TITLE = "TITLE";
    public static String CONTENT = "CONTENT";
    public static String RES_ICON = "RES_ICON";
    public static int DEF_ICONS = R.drawable.ic_launcher;
    public static String SP_NAME = "KeepAliveConfig";

/*    *//**
     * 运行模式
     *//*
    public static enum RunMode {
        *//**
         * 省电模式
         * 省电一些，但保活效果会差一点
         *//*
        ENERGY,
        *//**
         * 流氓模式
         * 相对耗电，但可造就不死之身
         *//*
        ROGUE
    }*/



}
