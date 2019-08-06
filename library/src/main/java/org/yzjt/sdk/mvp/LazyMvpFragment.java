package org.yzjt.sdk.mvp;

import android.os.Bundle;
import android.view.View;
import androidx.annotation.Nullable;
import org.yzjt.sdk.view.IContract;

/**
 * Created by LT on 2019/7/30.
 */
public abstract class LazyMvpFragment<P extends IContract.Presenter> extends BaseMvpFragment<P> {

    private boolean mHaveLoadData;
    private boolean mIsVisibleToUser;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        mIsVisibleToUser = isVisibleToUser;
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            onVisible();
        } else {
            onInvisible();
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(mIsVisibleToUser){
            onVisible();
        }
    }

    protected void onVisible() {
        mHaveLoadData = autoRefresh();
        if(mContentView != null && mPresenter != null && !mHaveLoadData){
            mHaveLoadData = true;
            mPresenter.initData();
            loadExtraData();
        }
    }

    /**
     * 是否自动可见时每次都刷新
     * @return true:每次可见刷新，false，只会加载一次
     */
    protected boolean autoRefresh(){
        return mHaveLoadData;
    }

    /**
     * 加载额外数据
     */
    protected void loadExtraData() {

    }

    protected void onInvisible() {

    }
}

