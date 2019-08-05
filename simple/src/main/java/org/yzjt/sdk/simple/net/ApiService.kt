package org.yzjt.sdk.simple.net

import io.reactivex.Observable
import org.yzjt.sdk.http.ResponseData
import org.yzjt.sdk.simple.entity.LoginEntity
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by LT on 2019/8/2.
 */
interface ApiService {

    @GET("user/login.php")
    fun login(@Query("username") username:String,@Query("password") password:String) : Observable<ResponseData<LoginEntity>>
}