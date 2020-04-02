package com.ykun.keepalive;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.ykun.live_library.KeepAliveManager;
import com.ykun.live_library.config.ForegroundNotification;
import com.ykun.live_library.config.ForegroundNotificationClickListener;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.ykun.live_library.config.RunMode.HIGH_POWER_CONSUMPTION;

public class MainActivity extends AppCompatActivity {

    private String TAG = getClass().getSimpleName();

    private SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("YYYY-MM-DD hh:mm:ss");


    private  Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            tvTime.setText(mSimpleDateFormat.format(new Date()));
            sendDelayMeg();
        }
    };
    private TextView tvTime;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTime = findViewById(R.id.tv_time);
        /**
         * 开启电量优化
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            KeepAliveManager.batteryOptimizations(getApplicationContext());
        }

    }

    /**
     * 启动代码适配的保活
     * @param view
     */
    public void startKeepAlive(View view) {
        start();
        sendDelayMeg();
    }

    private void sendDelayMeg() {
        handler.sendEmptyMessageDelayed(0, 1000);
    }

    /**
     * 停止保活
     * @param view
     */
    public void stopKeepAlive(View view) {
        KeepAliveManager.stopWork(getApplication());
    }

    public void start() {
        //启动保活服务
        KeepAliveManager.toKeepAlive(
                getApplication()
                , HIGH_POWER_CONSUMPTION,
                "进程保活",
                "Process: System(哥们儿) 我不想被杀死",
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

    /**
     * 开启系统设置保活
     * @param view
     */
    public void launch_system(View view) {
        sendDelayMeg();
        KeepAliveManager.launcherSyskeepAlive(getApplicationContext());
    }


    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeMessages(0);
        handler = null;
    }
}
