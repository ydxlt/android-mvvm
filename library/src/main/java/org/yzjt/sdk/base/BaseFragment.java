package org.yzjt.sdk.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import org.yzjt.sdk.nozzle.OnSingleClickListener;
import org.yzjt.sdk.util.ViewUtil;

public abstract class BaseFragment extends Fragment implements View.OnClickListener, OnSingleClickListener {
    protected String TAG = getClass().getSimpleName();
    private AppCompatActivity mActivity;
    protected View mContentView;
    protected Context mContext;
    private long[] antiDoubleClick = new long[2];
    private OnSingleClickListener mSingleClickListner;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContentView = getContentView(inflater,container);
        initView();
        mSingleClickListner = this;
        return mContentView;
    }

    protected abstract View getContentView(LayoutInflater inflater, ViewGroup container);

    protected abstract void initView();

    protected <T extends View> T findViewById(int resId){
        if(mContentView == null){
            return null;
        }
        return ViewUtil.findViewById(resId,mContentView);
    }

    /**
     * click事件,通过对外只暴露了OnSingleClickListener的监听器,
     * 让子类的activity去实现,这样防止用户的连点
     *
     * @param view
     */
    @Override
    public void onClick(View view) {
        antiDoubleClick[0] = antiDoubleClick[1];
        antiDoubleClick[1] = System.currentTimeMillis();
        if (antiDoubleClick[1] - antiDoubleClick[0] < 300) {
            Toast.makeText(view.getContext(), "请勿连续点击", Toast.LENGTH_SHORT).show();
        } else {
            if (mSingleClickListner != null) {
                mSingleClickListner.onSingleClick(view);
            }
        }
    }

    @Override
    public void onSingleClick(View view) {

    }
}
