package org.yzjt.sdk.util;

import android.content.Context;
import android.os.Looper;
import android.text.TextUtils;
import android.widget.Toast;
import org.yzjt.sdk.executor.MainThreadExecutor;

/**
 * Created by LT on 2019/1/17.
 */
public class ToastUtil {

    private static Toast sToast;

    public static MainThreadExecutor sMainThreadExecutor = new MainThreadExecutor();

    public static void toast(Context context, String msg){
        toast(context,msg, Toast.LENGTH_SHORT);
    }

    public static void toast(Context context, String msg, final int duration){
        if(TextUtils.isEmpty(msg)) return;
        if(sToast == null){
            if(isMainThread()){
                initToast(context,msg,duration);
            } else {
                sMainThreadExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        initToast(context,msg,duration);
                    }
                });
            }
        }
        if(isMainThread()) {
            sToast.setText(msg);
            sToast.show();
        } else {
            sMainThreadExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    sToast.setText(msg);
                    sToast.show();
                }
            });
        }
    }

    public static boolean isMainThread(){
        return Looper.myLooper() == Looper.getMainLooper();
    }

    private static void initToast(Context context, String msg, int duration){
        if(sToast == null){
            synchronized (ToastUtil.class){
                if(sToast == null){
                    sToast = Toast.makeText(context,msg,duration);
                }
            }
        }
    }
}
