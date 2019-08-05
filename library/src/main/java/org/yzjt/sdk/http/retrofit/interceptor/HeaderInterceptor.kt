package org.yzjt.sdk.http.retrofit.interceptor

import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by LT on 2019/7/31.
 */
class HeaderInterceptor : Interceptor {

    private var headers: Map<String, String>

    constructor(headers: Map<String, String>) {
        this.headers = headers
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request()
            .newBuilder()
        if (headers != null && headers.isNotEmpty()) {
            val keys = headers.keys
            for (headerKey in keys) {
                builder.addHeader(headerKey, headers.get(headerKey)!!).build()
            }
        }
        //请求信息
        return chain.proceed(builder.build())
    }
}