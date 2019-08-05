package org.yzjt.sdk.http.retrofit

import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import okhttp3.internal.platform.Platform
import org.yzjt.sdk.http.retrofit.interceptor.HeaderInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.IllegalArgumentException
import java.util.concurrent.TimeUnit

/**
 * Created by LT on 2019/8/2.
 */
object RetrofitProvider {

    // 超时时间
    private const val DEFAULT_TIMEOUT = 20L
    private const val HOST = "http://10.0.0.247"
    // 缓存时间
    private const val CACHE_TIMEOUT = 10 * 1024 * 1024

    private val retrofit:Retrofit by lazy {
        // config httpClient,you can custom it
        var okHttpClient = OkHttpClient.Builder()
//            .addInterceptor(HeaderInterceptor())
//            .addInterceptor(new CacheInterceptor(mContext))
//            .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
            .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .connectionPool(ConnectionPool(8, 15, TimeUnit.SECONDS))
            .build()
        Retrofit.Builder()
            .baseUrl(HOST)
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun get() : Retrofit = retrofit

    fun <T> createService(service:Class<T>) : T{
        if (service == null) {
            throw IllegalArgumentException("Api service is null!")
        }
        return retrofit.create(service)
    }
}
