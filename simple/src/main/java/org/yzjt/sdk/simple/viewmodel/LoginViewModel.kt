package org.yzjt.sdk.simple.viewmodel

import android.app.Application
import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import io.reactivex.functions.Consumer
import org.yzjt.sdk.mvvm.base.BaseViewModelImpl
import org.yzjt.sdk.simple.entity.LoginEntity
import org.yzjt.sdk.simple.repository.MainRepository

/**
 * Created by LT on 2019/8/1.
 */
class LoginViewModel : BaseViewModelImpl<MainRepository> {

    private var loginLiveData:MutableLiveData<LoginEntity> = MutableLiveData<LoginEntity>()
    private var loadLiveData:MutableLiveData<Boolean> = MutableLiveData<Boolean>()

    constructor(application: Application) : super(application)

    override fun getModel(): MainRepository {
        return MainRepository()
    }

    fun login(username:String,password:String){
        mModel?.let { it ->
            subscribe(it.login(username, password).doAfterTerminate {
                loadLiveData.value = false
            }.doOnSubscribe {
                loadLiveData.value = true
            }.subscribe({
                // success
                    response ->
                loginLiveData.value = response.data
            }, {
                // failure
                    throwable ->
                Log.e(TAG, "ex:" + throwable.message)
                Thread.sleep(2000)
                loginLiveData.value = LoginEntity("", "", "")
            }))
        }
    }

    fun getLoginLiveData() : MutableLiveData<LoginEntity> {
        return loginLiveData
    }

    fun getLoadLiveData() : MutableLiveData<Boolean> {
        return loadLiveData
    }
}