package org.yzjt.sdk.mvvm.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import org.yzjt.sdk.mvvm.viewmodel.BaseViewModel
import org.yzjt.sdk.view.BaseView

/**
 * Created by LT on 2019/7/31.
 */
interface MvvMView<T,VM : BaseViewModel<T>> : BaseView {

    /**
     * create the ViewModel
     *
     * @return Inherit the ViewModel of BaseViewModel
     */
    fun getViewModel(): VM?

    /**
     * UI page event monitoring method, generally used for event registration of the ViewModel layer to the View layer
     */
    fun initViewObservable() {

    }

    fun <T : ViewModel> createViewModel(activity: FragmentActivity, cls: Class<T>): T {
        return ViewModelProviders.of(activity).get(cls)
    }

    fun <T : ViewModel> createViewModel(fragment: Fragment, cls: Class<T>): T {
        return ViewModelProviders.of(fragment).get(cls)
    }
}