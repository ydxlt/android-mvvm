package org.yzjt.sdk.mvvm.base

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import org.yzjt.sdk.mvvm.view.MvvMView
import org.yzjt.sdk.mvvm.viewmodel.BaseViewModel
import org.yzjt.sdk.nozzle.OnSingleClickListener

/**
 * @author lt
 * @param <T> The type of data model
 * @param <VM> The type of view model
 */
abstract class BaseFragment<T,VM : BaseViewModel<T>> : Fragment(), MvvMView<T, VM>, OnSingleClickListener, View.OnClickListener  {

    val TAG = javaClass.simpleName
    protected var mContext: Context? = null
    protected lateinit var mContentView: View
    private val antiDoubleClick = LongArray(2)
    private var mSingleClickListener: OnSingleClickListener? = null
    protected var mViewModel:VM? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mContext = activity
    }

    /**
     * @return layout id
     *
     * @param savedInstanceState
     */
    override fun getContentViewId(savedInstanceState: Bundle?): Int {
        throw UnsupportedOperationException("Please override this method and return a effective layout id.")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mContentView = layoutInflater.inflate(getContentViewId(savedInstanceState),container)
        mViewModel = getViewModel()
        initView()
        return mContentView
    }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mSingleClickListener = this
        initViewObservable()
        initData()
    }

    fun <T : View> findViewById(resId:Int){
        if(mContentView == null){
            return
        }
        mContentView.findViewById<T>(resId)
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
//            Toast.makeText(view.context, "请勿连续点击", Toast.LENGTH_SHORT).show()
        } else {
            mSingleClickListener?.onSingleClick(view)
        }
    }

    override fun onSingleClick(view: View) {

    }
}