package org.yzjt.sdk.http

/**
 * Created by LT on 2019/8/2.
 */
data class ResponseData<T> (var code:Int,var message:String,var data:T?){

    fun isSuccess() : Boolean {
        return code == 1
    }
}