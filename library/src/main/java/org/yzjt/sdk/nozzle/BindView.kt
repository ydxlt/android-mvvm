package org.yzjt.sdk.nozzle

import android.os.Bundle

/**
 * Created by LT on 2019/8/1.
 */
interface BindView {

    /**
     * Initialize the id of the ViewModel
     *
     * @return BR的id
     * see androidx.databinding.ViewDataBinding
     */
    fun getVariableId(): Int

    /**
     * Initialize DataBinding
     *
     * @see androidx.databinding.ViewDataBinding
     */
    fun initDataBinding(savedInstanceState: Bundle?)
}