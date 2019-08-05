package org.yzjt.sdk.base

import android.app.Activity
import android.app.Application
import android.os.Bundle
import org.yzjt.sdk.manager.ActivityManager

/**
 * Created by LT on 2019/7/31.
 */
open class BaseApp : Application() {


    override fun onCreate() {
        super.onCreate()
        attachBaseContext(this)
    }

    companion object {
        private lateinit var sInstance: Application

        fun getInstance() : Application {
            if (sInstance == null) {
                throw NullPointerException("Please inherit BaseApp or call attachBaseContext.")
            }
            return sInstance
        }

        fun attachBaseContext(application: Application){
            sInstance = application
            sInstance.registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks{
                override fun onActivityPaused(p0: Activity?) {
                }

                override fun onActivityResumed(p0: Activity?) {
                }

                override fun onActivityStarted(p0: Activity?) {
                }

                override fun onActivityDestroyed(p0: Activity?) {
                    ActivityManager.remove(p0)
                }

                override fun onActivitySaveInstanceState(p0: Activity?, p1: Bundle?) {
                }

                override fun onActivityStopped(p0: Activity?) {
                }

                override fun onActivityCreated(p0: Activity?, p1: Bundle?) {
                    ActivityManager.put(p0)
                }
            })
        }
    }
}