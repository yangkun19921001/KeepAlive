package com.ykun.live_library;

import android.util.Log;

public class KeepAliveRuning implements IKeepAliveRuning {


    @Override
    public void onRuning() {
        //TODO--------------------------------------------
        Log.e("runing?KeepAliveRuning", "true");
    }

    @Override
    public void onStop() {
        Log.e("runing?KeepAliveRuning", "false");
    }
}
