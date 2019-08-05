package org.yzjt.sdk.nozzle

import android.os.Bundle

/**
 * Created by LT on 2019/7/31.
 */
interface BaseView {
    /**
     * Initialize data before method of initView()
     *
     * @author lt
     */
    fun init(){

    }

    /**
     * Initialize data,
     *
     * @author lt
     */
    fun initData() {

    }

    /**
     * Initialize View
     *
     * @author lt
     */
    fun initView() {

    }

    fun getContentViewId(savedInstanceState: Bundle?): Int
}