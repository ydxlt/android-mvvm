package org.yzjt.sdk.mvvm.base

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.yzjt.sdk.mvvm.viewmodel.BaseViewModel
import org.yzjt.sdk.mvvm.view.MvvMView
import org.yzjt.sdk.nozzle.OnSingleClickListener

/**
 * @author lt
 * @param <T> The type of data model
 * @param <VM> The type of view model
 */
abstract class BaseActivity<T,VM : BaseViewModel<T>> : AppCompatActivity(), MvvMView<T, VM>, OnSingleClickListener,View.OnClickListener {

    val TAG = javaClass.simpleName
    protected var mViewModel:VM? = null
    private val antiDoubleClick = LongArray(2)
    private val mSingleClickListener by lazy { this }
    protected val mContext: Context by lazy { this }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = getViewModel()
        initContentView(savedInstanceState)
        initStatusBar()
        init()
        initView()
        initViewObservable()
        initData()
    }

    open fun initContentView(savedInstanceState: Bundle?) {
        setContentView(getContentViewId(savedInstanceState))
    }

    /**
     * @return layout id
     *
     * @param savedInstanceState
     */
    override fun getContentViewId(savedInstanceState: Bundle?): Int {
        throw UnsupportedOperationException("Please override this method and return a effective layout id.")
    }

    /**
     * Initialize status bar，Override the lightStatusBar method to set the status bar theme color.
     * Rewrite empty implementation to cancel immersion status bar
     * @author lt
     */
    open fun initStatusBar() {
        // 5.0以上系统状态栏透明
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or if (lightStatusBar()) View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR else View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.setFlags(
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
            )
        }
    }

    open fun lightStatusBar(): Boolean {
        return false
    }

    /**
     * click事件,通过对外只暴露了OnSingleClickListener的监听器,
     * 让子类的activity去实现,这样防止用户的连点
     *
     * @param view
     */
    override fun onClick(view: View) {
        antiDoubleClick[0] = antiDoubleClick[1]
        antiDoubleClick[1] = System.currentTimeMillis()
        if (antiDoubleClick[1] - antiDoubleClick[0] < 300) {
            Toast.makeText(view.context, "请勿连续点击", Toast.LENGTH_SHORT).show()
        } else {
            if (mSingleClickListener != null) {
                mSingleClickListener.onSingleClick(view)
            }
        }
    }

    override fun onSingleClick(view: View) {

    }
}