package org.yzjt.sdk.base;

import android.content.Context;
import org.yzjt.sdk.view.IContract;

public abstract class BasePresenter<V extends IContract.View> implements IContract.Presenter<V> {

    protected String TAG = getClass().getSimpleName();
    private V mView;
    private Context mContext;

    public BasePresenter(Context context) {
        this.mContext = context;
    }

    @Override
    public void initData() {

    }

    @Override
    public void attachView(V view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mContext = null;
        mView = null;
    }

    public V getView() {
        return mView;
    }


    public Context getContext() {
        return mContext;
    }
}