package org.yzjt.sdk.nozzle

/**
 * Created by LT on 2019/7/31.
 */
internal interface Factory<T,P> {
    fun create(params:P): T
}
