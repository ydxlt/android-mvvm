package org.yzjt.sdk.util;

import android.util.Log;
import org.yzjt.sdk.BuildConfig;

/**
 * Created by LT on 2017/9/20.
 */

public class LogUtil {

    private static final boolean sDebug = BuildConfig.LOG_DEBUG;
    private static final String TAG = "LogUtils";
    private static final int LEVEL = 1;
    private static final int LEVEL_VERBOSE = 0;
    private static final int LEVEL_DEBUG = 1;
    private static final int LEVEL_INFO = 2;
    private static final int LEVEL_WRAN = 3;
    private static final int LEVEL_ERROR = 4;

    private LogUtil(){
        throw new RuntimeException("LogUtils is not be new");
    }

    public static void v(String tag,String msg){
        if(LEVEL >=LEVEL_VERBOSE && sDebug) {
            Log.v(tag, msg);
        }
    }

    public static void v(String msg){
        v(TAG,msg);
    }

    public static void d(String tag,String msg){
        if(LEVEL >=LEVEL_DEBUG && sDebug) {
            Log.d(tag, msg);
        }
    }

    public static void d(String msg){
        d(TAG,msg);
    }

    public static void i(String tag,String msg){
        if(LEVEL >=LEVEL_INFO && sDebug) {
            Log.i(tag, msg);
        }
    }

    public static void i(String msg){
        i(TAG,msg);
    }

    public static void w(String tag,String msg){
        if(LEVEL >=LEVEL_WRAN && sDebug) {
            Log.w(tag, msg);
        }
    }

    public static void w(String msg){
        w(TAG,msg);
    }

    public static void e(String tag,String msg){
        if(LEVEL >=LEVEL_ERROR && sDebug) {
            Log.e(tag, msg);
        }
    }

    public static void e(String msg){
        e(TAG,msg);
    }
}
