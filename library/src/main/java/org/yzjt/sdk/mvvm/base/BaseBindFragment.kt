package org.yzjt.sdk.mvvm.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import org.yzjt.sdk.mvvm.viewmodel.BaseViewModel
import org.yzjt.sdk.view.BindView

/**
 * @author lt
 * @param <T> The type of data model
 * @param <H> The type of ViewDataBinding
 * @param <VM> The type of view model
 */
abstract class BaseBindFragment<T,H : ViewDataBinding,VM : BaseViewModel<T>> : BaseFragment<T,VM>(),BindView {

    protected lateinit var mDataBinding:H

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mDataBinding = DataBindingUtil.inflate(inflater,getContentViewId(savedInstanceState),container,false)
        mViewModel = getViewModel()
        mDataBinding.setVariable(getVariableId(),mViewModel)
        // bind LiveData to DataBinding,and Lifecycle can be observer by LiveData
        mDataBinding.lifecycleOwner = this
        initView()
        return mDataBinding.root
    }

    override fun initDataBinding(savedInstanceState: Bundle?) {
        throw UnsupportedOperationException("Please override this method and init DataBinding. ")
    }


    override fun getVariableId(): Int {
        throw UnsupportedOperationException("Please override this method and return a effective variable id.")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mDataBinding?.unbind()
    }
}