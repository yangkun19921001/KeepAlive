package com.ykun.live_library.service;

import android.app.Notification;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.RemoteException;
import android.util.Log;

import com.ykun.keeplive.KeepAliveAidl;
import com.ykun.live_library.R;
import com.ykun.live_library.config.KeepAliveConfig;
import com.ykun.live_library.config.NotificationUtils;
import com.ykun.live_library.revices.NotificationClickReceiver;
import com.ykun.live_library.revices.OnepxReceiver;


public final class LocalService extends Service {
    private OnepxReceiver mOnepxReceiver;
    private ScreenStateReceiver screenStateReceiver;
    private boolean isPause = true;//控制暂停
    private MediaPlayer mediaPlayer;
    private MyBilder mBilder;
    private Handler handler;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("本地服务","：本地服务启动成功");
        if (mBilder == null) {
            mBilder = new MyBilder();
        }
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        isPause = pm.isScreenOn();
        if (handler == null) {
            handler = new Handler();
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBilder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //播放无声音乐
        if (mediaPlayer == null && KeepAliveConfig.runMode == KeepAliveConfig.RunMode.ROGUE) {
            mediaPlayer = MediaPlayer.create(this, R.raw.novioce);
            mediaPlayer.setVolume(0f, 0f);
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    if (!isPause) {
                        if (KeepAliveConfig.runMode == KeepAliveConfig.RunMode.ROGUE) {
                            play();
                        }
                    }
                }
            });
            play();
        }
        //像素保活
        if (mOnepxReceiver == null) {
            mOnepxReceiver = new OnepxReceiver();
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        intentFilter.addAction("android.intent.action.SCREEN_ON");
        registerReceiver(mOnepxReceiver, intentFilter);
        //屏幕点亮状态监听，用于单独控制音乐播放
        if (screenStateReceiver == null) {
            screenStateReceiver = new ScreenStateReceiver();
        }
        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction("_ACTION_SCREEN_OFF");
        intentFilter2.addAction("_ACTION_SCREEN_ON");
        registerReceiver(screenStateReceiver, intentFilter2);
        //启用前台服务，提升优先级
        Intent intent2 = new Intent(getApplicationContext(), NotificationClickReceiver.class);
        intent2.setAction(NotificationClickReceiver.CLICK_NOTIFICATION);
        Notification notification = NotificationUtils.createNotification(LocalService.this, getApplicationInfo().name,getApplicationInfo().name, R.drawable.ic_launcher, intent2);
        startForeground(13691, notification);
        //绑定守护进程
        try {
            Intent intent3 = new Intent(this, RemoteService.class);
            this.bindService(intent3, connection, Context.BIND_ABOVE_CLIENT);
        } catch (Exception e) {
            Log.i("RemoteService--",e.getMessage());
        }
        //隐藏服务通知
        try {
            if(Build.VERSION.SDK_INT < 25){
                startService(new Intent(this, HideForegroundService.class));
            }
        } catch (Exception e) {
            Log.i("HideForegroundService--",e.getMessage());

        }
        if (KeepAliveConfig.keepLiveService != null) {
            KeepAliveConfig.keepLiveService.onWorking();
        }
        return START_STICKY;
    }

    private void play() {
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
    }

    private void pause() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    private class ScreenStateReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(final Context context, Intent intent) {
            if (intent.getAction().equals("_ACTION_SCREEN_OFF")) {
                isPause = false;
                play();
            } else if (intent.getAction().equals("_ACTION_SCREEN_ON")) {
                isPause = true;
                pause();
            }
        }
    }

    private final class MyBilder extends KeepAliveAidl.Stub {

        @Override
        public void wakeUp(String title, String discription, int iconRes) throws RemoteException {

        }
    }

    private ServiceConnection connection = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Intent remoteService = new Intent(LocalService.this,
                    RemoteService.class);
            LocalService.this.startService(remoteService);
            Intent intent = new Intent(LocalService.this, RemoteService.class);
            LocalService.this.bindService(intent, connection,
                    Context.BIND_ABOVE_CLIENT);
            PowerManager pm = (PowerManager) LocalService.this.getSystemService(Context.POWER_SERVICE);
            boolean isScreenOn = pm.isScreenOn();
            if (isScreenOn) {
                sendBroadcast(new Intent("_ACTION_SCREEN_ON"));
            } else {
                sendBroadcast(new Intent("_ACTION_SCREEN_OFF"));
            }
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            try {
                if (mBilder != null && KeepAliveConfig.foregroundNotification != null) {
                    KeepAliveAidl guardAidl = KeepAliveAidl.Stub.asInterface(service);
                    guardAidl.wakeUp(KeepAliveConfig.foregroundNotification.getTitle(), KeepAliveConfig.foregroundNotification.getDescription(), KeepAliveConfig.foregroundNotification.getIconRes());
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbindService(connection);
        unregisterReceiver(mOnepxReceiver);
        unregisterReceiver(screenStateReceiver);
        if (KeepAliveConfig.keepLiveService != null) {
            KeepAliveConfig.keepLiveService.onStop();
        }
    }
}
