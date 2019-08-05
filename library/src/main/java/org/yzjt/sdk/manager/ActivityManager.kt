package org.yzjt.sdk.manager

import android.app.Activity
import java.util.*

/**
 * Created by LT on 2019/7/31.
 */
object ActivityManager {
    private var stack: Stack<Activity> = Stack()

    fun put(activity: Activity?) {
        if (null != activity) {
            this.stack.add(activity)
        }

    }

    fun remove(activity: Activity?) {
        if (null != activity) {
            this.stack.remove(activity)
        }

    }

    fun removeAll() {
        var i = 0
        val size = this.stack.size
        while (i < size) {
            if (null != this.stack[i]) {
                (this.stack[i] as Activity).finish()
            }
            ++i
        }
        this.stack.clear()
    }
}
