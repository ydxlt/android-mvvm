package org.yzjt.sdk.exception

import android.net.ParseException
import com.google.gson.JsonParseException
import com.google.gson.stream.MalformedJsonException
import org.apache.http.conn.ConnectTimeoutException
import org.json.JSONException
import retrofit2.HttpException
import java.lang.Exception
import java.net.ConnectException

/**
 * Created by LT on 2019/7/31.
 */
class ResponseException : Exception{

    var code:Int? = 0

    constructor(code:Int) : super(){
       this.code = code
    }

    constructor(message: String?,code:Int) : super(message) {
        this.code = code
    }

    constructor(message: String?,cause: Throwable?,code:Int) : super(cause){
        this.code = code
    }
}