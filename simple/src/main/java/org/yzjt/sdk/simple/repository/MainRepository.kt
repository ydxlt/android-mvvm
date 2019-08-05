package org.yzjt.sdk.simple.repository

import io.reactivex.Observable
import org.yzjt.sdk.http.ResponseData
import org.yzjt.sdk.http.retrofit.RetrofitProvider
import org.yzjt.sdk.mvvm.model.Model
import org.yzjt.sdk.rx.RxUtils
import org.yzjt.sdk.simple.entity.LoginEntity
import org.yzjt.sdk.simple.net.ApiService

/**
 * Created by LT on 2019/8/1.
 */
class MainRepository : Model {

    fun login(username:String,password:String) : Observable<ResponseData<LoginEntity>> {
        return RetrofitProvider.createService(ApiService::class.java)
            .login(username, password)
            .compose(RxUtils.schedulersTransformer())
    }

    override fun onCleared() {

    }
}
