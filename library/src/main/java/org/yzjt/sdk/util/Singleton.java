package org.yzjt.sdk.util;

/**
 * Created by LT on 2019/4/24.
 */
public abstract class Singleton<T> {

    protected T mInstance;

    protected abstract T create();

    public final T get(){
        synchronized (this){
            if(mInstance == null){
                mInstance = create();
            }
            return mInstance;
        }
    }
}
