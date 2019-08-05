package org.yzjt.sdk.mvvm.model

/**
 * Created by LT on 2019/7/31.
 */
interface Model {

    /**
     * ViewModel销毁时清除Model，与ViewModel共消亡。Model层同样不能持有长生命周期对象
     */
    fun onCleared()
}