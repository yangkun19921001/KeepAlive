package com.ykun.keeplive;

interface KeepAliveAidl {
    //相互唤醒服务
    void wakeUp(String title, String discription, int iconRes);
}