package org.yzjt.sdk.rx

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by LT on 2019/8/5.
 */
object RxSubscriptions {

    private val mCompositeDisposable: CompositeDisposable by lazy {
        CompositeDisposable()
    }

    fun add(disposable: Disposable){
        mCompositeDisposable.add(disposable)
    }

    fun remove(disposable: Disposable){
        mCompositeDisposable.remove(disposable)
    }

    fun clear(){
        mCompositeDisposable.clear()
    }

    fun dispose(){
        mCompositeDisposable.dispose()
    }
}