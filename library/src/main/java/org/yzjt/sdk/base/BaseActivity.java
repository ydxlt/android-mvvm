package org.yzjt.sdk.base;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import org.yzjt.sdk.nozzle.OnSingleClickListener;


public class BaseActivity extends AppCompatActivity implements View.OnClickListener, OnSingleClickListener {

    public final String TAG = getClass().getSimpleName();

    public Context mContext;
    private long[] antiDoubleClick = new long[2];
    private OnSingleClickListener mSingleClickListner;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        initStatusbar();
        initOther();
        initView();
        mSingleClickListner = this;
        initData();
    }

    protected void initData() {

    }

    protected void initView() {

    }


    protected void initOther() {

    }

    protected void initStatusbar() {
        // 5.0以上系统状态栏透明
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | (lightStatusBar()?View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR:View.SYSTEM_UI_FLAG_LAYOUT_STABLE));
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    /**
     * 是否让状态栏高亮
     * @return
     */
    protected boolean lightStatusBar(){
        return false;
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
