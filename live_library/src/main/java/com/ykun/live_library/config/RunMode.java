package com.ykun.live_library.config;

import android.support.annotation.IntDef;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class RunMode {
    /**
     * 省电模式
     * 省电一些，但保活效果会差一点
     */
    public static final int POWER_SAVING = 0;
    /**
     * 流氓模式
     * 相对耗电，但可造就不死之身
     */
    public static final int HIGH_POWER_CONSUMPTION = 1;


    @IntDef(flag = true, value = {POWER_SAVING, HIGH_POWER_CONSUMPTION})
    @Target({ElementType.PARAMETER, ElementType.METHOD, ElementType.FIELD})
    @Retention(RetentionPolicy.SOURCE)
    public static @interface Model {

    }

    private static @Model
    int value = POWER_SAVING;

    public static void setShape(@Model int values) {
        value = values;
    }

    @Model
    public static int getShape() {
        return value;
    }
}