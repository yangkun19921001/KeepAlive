package com.ykun.live_library.config;

/**
 * 进程保活需要配置的属性
 */
public class KeepAliveConfig {

    /**
     * Job 的时间
     */
    public static final int JOB_TIME = 1000;
    public static ForegroundNotification foregroundNotification = null;
    public static KeepLiveService keepLiveService = null;
    public static RunMode runMode = null;

    /**
     * 运行模式
     */
    public static enum RunMode {
        /**
         * 省电模式
         * 省电一些，但保活效果会差一点
         */
        ENERGY,
        /**
         * 流氓模式
         * 相对耗电，但可造就不死之身
         */
        ROGUE
    }
}
