package com.ykun.live_library.onepx;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

public final class OnePixelActivity extends Activity {
    //注册广播接受者   当屏幕开启结果成功结束一像素的activity
    BroadcastReceiver br;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设定一像素的activity
        Window window = getWindow();
        window.setGravity(Gravity.START | Gravity.TOP);
        WindowManager.LayoutParams params = window.getAttributes();
        params.x = 0;
        params.y = 0;
        params.height = 1;
        params.width = 1;
        window.setAttributes(params);
        //在一像素activity里注册广播接受者    接受到广播结束掉一像素
        br = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                finish();
            }
        };
        registerReceiver(br, new IntentFilter("finish activity"));
        checkScreenOn("onCreate");
    }

    @Override
    protected void onDestroy() {
        try {
            unregisterReceiver(br);
        } catch (IllegalArgumentException e) {
        }
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkScreenOn("onResume");
    }

    private void checkScreenOn(String methodName) {
        PowerManager pm = (PowerManager) OnePixelActivity.this.getSystemService(Context.POWER_SERVICE);
        boolean isScreenOn = pm.isScreenOn();
        if (isScreenOn) {
            finish();
        }
    }
}
