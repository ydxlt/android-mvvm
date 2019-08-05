package org.yzjt.sdk.http.retrofit.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import org.yzjt.sdk.http.retrofit.ResponseBodyProgressWrap

/**
 * Created by LT on 2019/7/31.
 */
class ProgressInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalResponse = chain.proceed(chain.request())
        return originalResponse.newBuilder()
            .body(originalResponse.body()?.let { ResponseBodyProgressWrap(it) })
            .build()
    }
}