package com.ykun.keepalive;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.ykun.live_library.KeepAliveManager;
import com.ykun.live_library.config.ForegroundNotification;
import com.ykun.live_library.config.ForegroundNotificationClickListener;
import com.ykun.live_library.config.KeepAliveConfig;
import com.ykun.live_library.config.KeepLiveService;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        NotificationUtils.createNotification(getApplicationContext(),"");
    }

    public void startKeepAlive(View view) {
        start();
    }
    public void start() {
        //定义前台服务的默认样式。即标题、描述和图标
        ForegroundNotification foregroundNotification = new ForegroundNotification("测试", "描述", R.mipmap.ic_launcher,
                //定义前台服务的通知点击事件
                new ForegroundNotificationClickListener() {
                    @Override
                    public void foregroundNotificationClick(Context context, Intent intent) {
                    }
                });
        //启动保活服务
        KeepAliveManager.startWork(getApplication(), KeepAliveConfig.RunMode.ENERGY, foregroundNotification,
                //你需要保活的服务，如socket连接、定时任务等，建议不用匿名内部类的方式在这里写
                new KeepLiveService() {
/*                    *
                     * 运行中
                     * 由于服务可能会多次自动启动，该方法可能重复调用
                     */
                    @Override
                    public void onWorking() {
                    }
/*
                                        *
                     * 服务终止
                     * 由于服务可能会被多次终止，该方法可能重复调用，需同onWorking配套使用，如注册和注销broadcast
                     */
                    @Override
                    public void onStop() {

                    }
                }
        );
    }
}
