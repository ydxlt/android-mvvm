package org.yzjt.sdk.weiget.loading;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import org.yzjt.sdk.R;


/**
 * Created by LT on 2019/4/22.
 */
public class ProgressLoadingHelper implements LoadingHelper {

    private Context mContext;
    private View mRootView;
    private boolean isAttached = false;
    private View mBrotherView;
    private View mLl_nothing;
    private TextView mTv_retry;

    public ProgressLoadingHelper(Context context) {
        mContext = context;
    }

    @Override
    public void attachTo(ViewGroup parent, View brother){
        if(parent == null){
            throw new NullPointerException("ProgressLoadingHelper can not attach to a null View,See the method of attachTo.");
        }
        if(isAttached){
            throw new RuntimeException("ProgressLoadingHelper Already attachTo，can not attach to "+ parent + " again.");
        }
        mRootView = LayoutInflater.from(mContext).inflate(R.layout.layout_progress_loading,null);
        mLl_nothing = mRootView.findViewById(R.id.ll_nothing);
        mTv_retry = (TextView) mRootView.findViewById(R.id.tv_retry);
        isAttached = true;
        mBrotherView = brother;
        parent.addView(mRootView,new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mBrotherView.setVisibility(View.GONE);
        mRootView.setVisibility(View.VISIBLE);
    }

    @Override
    public View getView() {
        return mRootView;
    }

    @Override
    public void showLoading(String msg) {
        if(!isAttached){
            throw new RuntimeException("ProgressLoadingHelper have not attach a View,See the method of attachTo.");
        }
        mBrotherView.setVisibility(View.GONE);
        mRootView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        if(!isAttached){
            throw new RuntimeException("ProgressLoadingHelper have not attach a View,See the method of attachTo.");
        }
        mBrotherView.setVisibility(View.VISIBLE);
        mRootView.setVisibility(View.GONE);
    }

    @Override
    public void showError(String msg) {
        if(!isAttached){
            throw new RuntimeException("ProgressLoadingHelper have not attach a View,See the method of attachTo.");
        }
        mBrotherView.setVisibility(View.GONE);
        mRootView.setVisibility(View.VISIBLE);
        mLl_nothing.setVisibility(View.GONE);
        if(!TextUtils.isEmpty(msg)){
            mTv_retry.setText(msg);
        }
    }

    @Override
    public void showEmpty() {
        if(!isAttached){
            throw new RuntimeException("ProgressLoadingHelper have not attach a View,See the method of attachTo.");
        }
        mRootView.setVisibility(View.VISIBLE);
        mBrotherView.setVisibility(View.GONE);
        mLl_nothing.setVisibility(View.VISIBLE);
        mTv_retry.setVisibility(View.GONE);
    }

    @Override
    public void setOnErrorClickListener(View.OnClickListener onErrorClickListener) {
        if(mTv_retry != null) {
            mTv_retry.setOnClickListener(onErrorClickListener);
        }
    }
}
