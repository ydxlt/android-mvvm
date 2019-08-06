package org.yzjt.sdk.mvvm.base

import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import org.yzjt.sdk.mvvm.model.Model

/**
 * Created by LT on 2019/8/5.
 */
open class BaseModelImpl : Model {

    /* 管理RxJava的Observable，防止异步操作造成的内存泄漏 */
    protected val mCompositeDisposable: CompositeDisposable by lazy {
        CompositeDisposable()
    }

    /**
     * manager disposable
     */
    fun subscribe(disposable:Disposable){
        mCompositeDisposable.add(disposable)
    }

    override fun onCleared() {
        //ViewModel销毁时会执行，同时取消所有异步任务
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear()
        }
    }
}