package com.ykun.keepalive;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.ykun.live_library.KeepAliveManager;
import com.ykun.live_library.config.ForegroundNotification;
import com.ykun.live_library.config.ForegroundNotificationClickListener;

import static com.ykun.live_library.config.RunMode.POWER_SAVING;

public class MainActivity extends AppCompatActivity {

    private String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startKeepAlive(View view) {
        start();
    }

    public void stopKeepAlive(View view) {
        KeepAliveManager.stopWork(getApplication());
    }

    public void start() {
        //启动保活服务
        KeepAliveManager.startWork(
                getApplication()
                , POWER_SAVING,
                "hahahah",
                "123123123",
                R.mipmap.ic_launcher,
                new ForegroundNotification(
                        //定义前台服务的通知点击事件
                        new ForegroundNotificationClickListener() {
                            @Override
                            public void foregroundNotificationClick(Context context, Intent intent) {
                                Log.d("JOB-->", " foregroundNotificationClick");
                            }
                        })
        );
    }


}
