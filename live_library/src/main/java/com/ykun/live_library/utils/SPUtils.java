package com.ykun.live_library.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.ykun.live_library.R;
import com.ykun.live_library.pro_sp.PreferenceUtil;

@SuppressLint("ApplySharedPref")
public final class SPUtils {

    private static Context mContext;
    private static SharedPreferences mPreferences;

    /**
     * Return the single {@link SPUtils} instance
     *
     * @param spName The name of sp.
     * @return the single {@link SPUtils} instance
     */
    public static SPUtils getInstance(Context context, String spName) {
        mContext = context;
        init(mContext);
        return new SPUtils();
    }


    /**
     * Put the string value in sp.
     *
     * @param key   The key of sp.
     * @param value The value of sp.
     */
    public void put(@NonNull final String key, final String value) {
//        put(key, value, false);
        putS(key, value);
    }

    /**
     * Return the string value in sp.
     *
     * @param key The key of sp.
     * @return the string value if sp exists or {@code ""} otherwise
     */
    public String getString(@NonNull final String key) {
        return getS(key);
    }

    /**
     * Return the string value in sp.
     *
     * @param key          The key of sp.
     * @param defaultValue The default value if the sp doesn't exist.
     * @return the string value if sp exists or {@code defaultValue} otherwise
     */
    public String getString(@NonNull final String key, final String defaultValue) {
        return getS(key);
    }

    /**
     * Put the int value in sp.
     *
     * @param key   The key of sp.
     * @param value The value of sp.
     */
    public void put(@NonNull final String key, final int value) {
//        put(key, value, false);
        putI(key, value);
    }

    /**
     * Return the int value in sp.
     *
     * @param key The key of sp.
     * @return the int value if sp exists or {@code -1} otherwise
     */
    public int getInt(@NonNull final String key) {
        return getI(key, R.drawable.ic_launcher);
    }

    /**
     * Return the int value in sp.
     *
     * @param key          The key of sp.
     * @param defaultValue The default value if the sp doesn't exist.
     * @return the int value if sp exists or {@code defaultValue} otherwise
     */
    public int getInt(@NonNull final String key, final int defaultValue) {
        return getI(key, defaultValue);
    }


    public static void init(Context context) {
        if (mPreferences == null)
            mPreferences = PreferenceUtil.getSharedPreference(context, "DEV_YKUN");
    }

    public static String getS(String key) {
        return mPreferences.getString(key, "");
    }

    public static int getI(String key, int def) {
        return mPreferences.getInt(key, def);
    }

    public static void putS(String key, String def) {
        mPreferences.edit().putString(key, def).apply();
    }

    public static void putI(String key, int def) {
        mPreferences.edit().putInt(key, def).apply();
    }
}