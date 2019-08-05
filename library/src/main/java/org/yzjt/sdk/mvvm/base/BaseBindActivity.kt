package org.yzjt.sdk.mvvm.base

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import org.yzjt.sdk.mvvm.viewmodel.BaseViewModel
import org.yzjt.sdk.nozzle.BindView

/**
 * @author lt
 * @param <T> The type of data model
 * @param <H> The type of ViewDataBinding
 * @param <VM> The type of view model
 */
abstract class BaseBindActivity<T,H : ViewDataBinding,VM : BaseViewModel<T>> : BaseActivity<T, VM>(),BindView {

    protected lateinit var mDataBinding:H

    override fun initContentView(savedInstanceState: Bundle?) {
        mDataBinding = DataBindingUtil.setContentView(this,getContentViewId(savedInstanceState))
        mDataBinding.setVariable(getVariableId(),mViewModel)
        // bind LiveData to DataBinding,and Lifecycle can be observer by LiveData
        mDataBinding.lifecycleOwner = this
    }

    override fun initDataBinding(savedInstanceState: Bundle?) {
        throw UnsupportedOperationException("Please override this method and init DataBinding. ")
    }


    override fun getVariableId(): Int {
        throw UnsupportedOperationException("Please override this method and return a effective variable id.")
    }

    override fun onDestroy() {
        super.onDestroy()
        mDataBinding?.unbind()
    }
}