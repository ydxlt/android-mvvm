package org.yzjt.sdk.mvvm.event

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer

/**
 * Created by LT on 2019/7/31.
 */
class UIEventLiveData<T> : SingleLiveData<T>() {

    val showDialogEvent: SingleLiveData<String> by lazy {
        create<String>()
    }

    val dismissDialogEvent: SingleLiveData<Void> by lazy{
        create<Void>()
    }

    val startActivityEvent: SingleLiveData<Map<String, Any>> by lazy {
        create<Map<String, Any>>()
    }

    val startContainerActivityEvent: SingleLiveData<Map<String, Any>> by lazy {
        create<Map<String, Any>>()
    }

    val finishEvent: SingleLiveData<Void> by lazy{
        create<Void>()
    }
    val onBackPressedEvent: SingleLiveData<Void> by lazy {
        create<Void>()
    }


    private fun <T> create(): SingleLiveData<T> {
        return SingleLiveData<T>()
    }

    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        super.observe(owner, observer)
    }
}
