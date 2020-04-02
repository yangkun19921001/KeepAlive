package com.ykun.live_library;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;

/**
 * <pre>
 *     author  : devyk on 2020-04-02 20:19
 *     blog    : https://juejin.im/user/578259398ac2470061f3a3fb/posts
 *     github  : https://github.com/yangkun19921001
 *     mailbox : yang1001yk@gmail.com
 *     desc    : This is DevicesLaunchConfig
 * </pre>
 */
public class DevicesLaunchConfig {
    private static Context mContext;

    /**
     * @param context
     */
    public static void launchSystemKeepAlive(Context context) {
        mContext = context.getApplicationContext();
        if (isHuawei())
            goHuaweiSetting();
        else if (isLeTV())
            goLetvSetting();
        else if (isMeizu())
            goMeizuSetting();
        else if (isSamsung())
            goSamsungSetting();
        else if (isVIVO())
            goVIVOSetting();
        else if (isXiaomi())
            goXiaomiSetting();
    }


    private static boolean isHuawei() {
        if (Build.BRAND == null) {
            return false;
        } else {
            return Build.BRAND.toLowerCase().equals("huawei") || Build.BRAND.toLowerCase().equals("honor");
        }
    }


    private static void goHuaweiSetting() {
        try {
            showActivity("com.huawei.systemmanager",
                    "com.huawei.systemmanager.startupmgr.ui.StartupNormalAppListActivity");
        } catch (Exception e) {
            showActivity("com.huawei.systemmanager",
                    "com.huawei.systemmanager.optimize.bootstart.BootStartActivity");
        }
    }


    public static boolean isXiaomi() {
        return Build.BRAND != null && Build.BRAND.toLowerCase().equals("xiaomi");
    }

    private static void goXiaomiSetting() {
        showActivity("com.miui.securitycenter",
                "com.miui.permcenter.autostart.AutoStartManagementActivity");
    }

    public static boolean isOPPO() {
        return Build.BRAND != null && Build.BRAND.toLowerCase().equals("oppo");
    }

    private static void goOPPOSetting() {
        try {
            showActivity("com.coloros.phonemanager");
        } catch (Exception e1) {
            try {
                showActivity("com.oppo.safe");
            } catch (Exception e2) {
                try {
                    showActivity("com.coloros.oppoguardelf");
                } catch (Exception e3) {
                    showActivity("com.coloros.safecenter");
                }
            }
        }
    }

    public static boolean isVIVO() {
        return Build.BRAND != null && Build.BRAND.toLowerCase().equals("vivo");
    }

    private static void goVIVOSetting() {
        showActivity("com.iqoo.secure");
    }

    public static boolean isMeizu() {
        return Build.BRAND != null && Build.BRAND.toLowerCase().equals("meizu");
    }

    private static void goMeizuSetting() {
        showActivity("com.meizu.safe");
    }

    public static boolean isSamsung() {
        return Build.BRAND != null && Build.BRAND.toLowerCase().equals("samsung");
    }

    private static void goSamsungSetting() {
        try {
            showActivity("com.samsung.android.sm_cn");
        } catch (Exception e) {
            showActivity("com.samsung.android.sm");
        }
    }

    public static boolean isLeTV() {
        return Build.BRAND != null && Build.BRAND.toLowerCase().equals("letv");
    }

    private static void goLetvSetting() {
        showActivity("com.letv.android.letvsafe",
                "com.letv.android.letvsafe.AutobootManageActivity");
    }

    public static boolean isSmartisan() {
        return Build.BRAND != null && Build.BRAND.toLowerCase().equals("smartisan");
    }

    private static void goSmartisanSetting() {
        showActivity("com.smartisanos.security");
    }

    /**
     * 跳转到指定应用的首页
     */
    private static void showActivity(@NonNull String packageName) {
        Intent intent = mContext.getPackageManager().getLaunchIntentForPackage(packageName);
        mContext.startActivity(intent);
    }

    /**
     * 跳转到指定应用的指定页面
     */
    private static void showActivity(@NonNull String packageName, @NonNull String activityDir) {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(packageName, activityDir));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }

}
