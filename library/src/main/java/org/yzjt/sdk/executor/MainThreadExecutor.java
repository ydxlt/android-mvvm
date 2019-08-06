package org.yzjt.sdk.executor;

import android.os.Handler;
import android.os.Looper;
import androidx.annotation.NonNull;

import java.util.concurrent.Executor;

/**
 * Created by LT on 2019/1/15.
 */
public class MainThreadExecutor implements Executor {

    private final Handler mHandler;

    public MainThreadExecutor() {
        mHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void execute(@NonNull Runnable runnable) {
        mHandler.post(runnable);
    }

    public void execute(@NonNull Runnable runnable, long delay) {
        mHandler.postDelayed(runnable,delay);
    }

    public static boolean isMainThread(){
        return Looper.myLooper() == Looper.getMainLooper();
    }
}
