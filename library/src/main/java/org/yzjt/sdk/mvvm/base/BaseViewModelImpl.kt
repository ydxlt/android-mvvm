package org.yzjt.sdk.mvvm.base

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import org.yzjt.sdk.http.ResponseData
import org.yzjt.sdk.mvvm.viewmodel.BaseViewModel
import org.yzjt.sdk.mvvm.model.Model

/**
 * Created by LT on 2019/8/1.
 */
open abstract class BaseViewModelImpl<T : Model> : AndroidViewModel, BaseViewModel<T> {

    val TAG = javaClass.simpleName

    // 管理RxJava的Observable，防止异步操作造成的内存泄漏
    protected val mCompositeDisposable: CompositeDisposable by lazy {
        CompositeDisposable()
    }

    /**
     * manager disposable
     */
    fun subscribe(disposable: Disposable){
        mCompositeDisposable.add(disposable)
    }

    protected var mModel:T? = null

    constructor(application: Application) : super(application){
        mModel = getModel()
    }

    fun <T> consumerError(throwable: Throwable) : ResponseData<T> {
        var msg = throwable.message;
        if(msg == null){
            msg = "unknow error."
        }
        return  ResponseData<T>(0, msg,null)
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG,"onCleared()")
        mModel?.onCleared()
        //ViewModel销毁时会执行，同时取消所有异步任务
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear()
        }
    }
}