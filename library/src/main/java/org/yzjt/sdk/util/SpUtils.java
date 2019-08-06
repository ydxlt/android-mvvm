package org.yzjt.sdk.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

/**
 * Created by LT on 2018/8/21.
 */
public class SpUtils {

    private SharedPreferences sp;

    /**
     * 根据名称获取sp对象
     *
     * @param context
     * @param name
     * @return
     */
    private SpUtils(Context context, String name) {
        sp = context.getSharedPreferences(name, Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB ? Context.MODE_PRIVATE : Context.MODE_MULTI_PROCESS);
    }

    public static SpUtils getInstance(Context context, String name) {
        return new SpUtils(context, name);
    }

    public SpUtils putInt(String key, int value) {
        sp.edit().putInt(key, value).commit();
        return this;
    }

    public SharedPreferences getSp() {
        return sp;
    }

    public int getInt(String key, int dValue) {
        return sp.getInt(key, dValue);
    }

    public SpUtils putLong(String key, long value) {
        sp.edit().putLong(key, value).commit();
        return this;
    }

    public long getLong(String key, Long dValue) {
        return sp.getLong(key, dValue);
    }

    public SpUtils putFloat(String key, float value) {
        sp.edit().putFloat(key, value).commit();
        return this;
    }

    public Float getFloat(String key, Float dValue) {
        return sp.getFloat(key, dValue);
    }

    public SpUtils putBoolean(String key, boolean value) {
        sp.edit().putBoolean(key, value).commit();
        return this;
    }

    public Boolean getBoolean(String key, boolean dValue) {
        return sp.getBoolean(key, dValue);
    }

    public SpUtils putString(String key, String value) {
        sp.edit().putString(key, value).commit();
        return this;
    }

    public String getString(String key, String dValue) {
        return sp.getString(key, dValue);
    }

    public void remove(String key) {
        if (isExist(key)) {
            SharedPreferences.Editor editor = sp.edit();
            editor.remove(key);
            editor.commit();
        }
    }

    public boolean isExist(String key) {
        return sp.contains(key);
    }

    public void clear(){
        sp.edit().clear().commit();
    }
}
