package org.yzjt.sdk.base;

import android.os.Bundle;
import android.view.View;
import androidx.annotation.Nullable;

/**
 * Created by LT on 2019/1/18.
 */
public abstract class BaseLazyFragment extends BaseFragment {

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
        if(mContentView != null && !mHaveLoadData){
            mHaveLoadData = true;
            loadData();
            loadExtraData();
        }
    }

    protected void loadData() {

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
