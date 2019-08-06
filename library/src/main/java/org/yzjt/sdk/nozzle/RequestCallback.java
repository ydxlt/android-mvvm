package org.yzjt.sdk.nozzle;

import org.yzjt.sdk.view.IContract;

/**
 * Created by LT on 2018/12/17.
 */
public interface RequestCallback<T> extends IContract.View{

    /**
     * 请求中
     */
    void onRequest();

    /**
     * 请求成功
     * @param t
     */
    void onSuccess(T t);

    /**
     * 请求失败
     * @param msg
     */
    void onFailure(String msg);

    /**
     * 请求完成
     */
    void onComplete();
}
