package org.yzjt.sdk.mvp;


import org.yzjt.sdk.base.BaseActivity;
import org.yzjt.sdk.view.IContract;

public abstract class BaseMvpActivity<T extends IContract.Presenter> extends BaseActivity implements IContract.View {

    protected T mPresenter;

    @Override
    protected void initOther() {
        super.initOther();
        mPresenter = initPresenter();
        if(mPresenter != null) {
            mPresenter.attachView(this);
        }
    }

    @Override
    protected void onDestroy() {
        if(mPresenter != null) {
            mPresenter.detachView();
        }
        super.onDestroy();
    }

    public abstract T initPresenter();
}
