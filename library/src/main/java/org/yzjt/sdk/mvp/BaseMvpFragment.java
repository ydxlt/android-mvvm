package org.yzjt.sdk.mvp;

import android.os.Bundle;
import androidx.annotation.Nullable;
import org.yzjt.sdk.base.BaseFragment;
import org.yzjt.sdk.view.IContract;


public abstract class BaseMvpFragment<T extends IContract.Presenter> extends BaseFragment implements IContract.View{

    protected T mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = initPresenter();
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
    }

    @Override
    public void onDestroy() {
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        super.onDestroy();
    }

    public abstract T initPresenter();
}
