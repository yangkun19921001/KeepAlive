package com.ykun.live_library;

import android.app.Application;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.Log;

import com.ykun.live_library.config.ForegroundNotification;
import com.ykun.live_library.config.KeepAliveConfig;
import com.ykun.live_library.config.KeepLiveService;
import com.ykun.live_library.service.JobHandlerService;
import com.ykun.live_library.service.LocalService;
import com.ykun.live_library.service.RemoteService;
import com.ykun.live_library.utils.KeepAliveUtils;

/**
 * 进程保活管理
 */
public class KeepAliveManager {
    private  static final String TAG = "KeepAliveManager";

    /**
     * 启动保活
     *
     * @param application            your application
     * @param foregroundNotification 前台服务
     * @param keepLiveService        保活业务
     */
    public static void startWork(@NonNull Application application, @NonNull KeepAliveConfig.RunMode runMode, ForegroundNotification foregroundNotification, @NonNull KeepLiveService keepLiveService) {
        if (KeepAliveUtils.isRunning(application)) {
            KeepAliveConfig.foregroundNotification = foregroundNotification;
            KeepAliveConfig.keepLiveService = keepLiveService;
            KeepAliveConfig.runMode = runMode;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                //启动定时器，在定时器中启动本地服务和守护进程
                JobHandlerService.startJob(application);
            } else {
                //启动本地服务
                Intent localIntent = new Intent(application, LocalService.class);
                //启动守护进程
                Intent guardIntent = new Intent(application, RemoteService.class);
                application.startService(localIntent);
                application.startService(guardIntent);
            }
        }
    }

    public static void stopWork(Application application){
        try {
            KeepAliveConfig.foregroundNotification = null;
            KeepAliveConfig.keepLiveService = null;
            KeepAliveConfig.runMode = null;
            JobHandlerService.stopJob();
            //启动本地服务
            Intent localIntent = new Intent(application, LocalService.class);
            //启动守护进程
            Intent guardIntent = new Intent(application, RemoteService.class);
            application.stopService(localIntent);
            application.stopService(guardIntent);
        }catch (Exception e){
            Log.e(TAG,"stopWork-->"+e.getMessage());
        }

    }


}
