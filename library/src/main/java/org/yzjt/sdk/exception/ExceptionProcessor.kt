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

class ExceptionProcessor   {

    companion object {
        fun process(e: Throwable): Exception {
            val ex: ResponseException
            if (e is HttpException) {
                when (e.code()) {
                    ExceptionProcessor.Code.UNAUTHORIZED -> {
                        ex = ResponseException("操作未授权",e, ExceptionProcessor.ERROR.HTTP_ERROR)
                    }
                    ExceptionProcessor.Code.FORBIDDEN -> {
                        ex = ResponseException("请求被拒绝",e, ExceptionProcessor.ERROR.HTTP_ERROR)
                    }
                    ExceptionProcessor.Code.NOT_FOUND -> {
                        ex = ResponseException("资源不存在",e, ExceptionProcessor.ERROR.HTTP_ERROR)
                    }
                    ExceptionProcessor.Code.REQUEST_TIMEOUT -> {
                        ex = ResponseException("服务器执行超时",e, ExceptionProcessor.ERROR.HTTP_ERROR)
                    }
                    ExceptionProcessor.Code.INTERNAL_SERVER_ERROR -> {
                        ex = ResponseException("服务器内部错误",e, ExceptionProcessor.ERROR.HTTP_ERROR)
                    }

                    ExceptionProcessor.Code.SERVICE_UNAVAILABLE -> {
                        ex = ResponseException("服务器不可用",e, ExceptionProcessor.ERROR.HTTP_ERROR)
                    }

                    else ->{
                        ex = ResponseException("网络错误",e, ExceptionProcessor.ERROR.HTTP_ERROR)
                    }
                }
                return ex
            } else if (e is JsonParseException
                || e is JSONException
                || e is ParseException || e is MalformedJsonException
            ) {
                ex = ResponseException("解析错误",e, ExceptionProcessor.ERROR.PARSE_ERROR)
                return ex
            } else if (e is ConnectException) {
                ex = ResponseException("连接失败",e, ExceptionProcessor.ERROR.NETWORD_ERROR)
                return ex
            } else if (e is javax.net.ssl.SSLException) {
                ex = ResponseException("证书验证失败",e, ExceptionProcessor.ERROR.SSL_ERROR)
                return ex
            } else if (e is ConnectTimeoutException) {
                ex = ResponseException( "连接超时",e, ExceptionProcessor.ERROR.TIMEOUT_ERROR)
                return ex
            } else if (e is java.net.SocketTimeoutException) {
                ex = ResponseException("连接超时",e, ExceptionProcessor.ERROR.TIMEOUT_ERROR)
                return ex
            } else if (e is java.net.UnknownHostException) {
                ex = ResponseException("主机地址未知",e, ExceptionProcessor.ERROR.TIMEOUT_ERROR)
            } else {
                ex = ResponseException("未知错误",e, ExceptionProcessor.ERROR.UNKNOWN)
            }
            return ex;
        }

    }

    /**
     * 约定异常 这个具体规则需要与服务端或者领导商讨定义
     */
    internal object ERROR {
        /**
         * 未知错误
         */
        val UNKNOWN = 1000
        /**
         * 解析错误
         */
        val PARSE_ERROR = 1001
        /**
         * 网络错误
         */
        val NETWORD_ERROR = 1002
        /**
         * 协议出错
         */
        val HTTP_ERROR = 1003

        /**
         * 证书出错
         */
        val SSL_ERROR = 1005

        /**
         * 连接超时
         */
        val TIMEOUT_ERROR = 1006
    }

    /**
     * 约定Code,对应于http的code
     */
    internal object Code {
        val UNAUTHORIZED = 401
        val FORBIDDEN = 403
        val NOT_FOUND = 404
        val REQUEST_TIMEOUT = 408
        val INTERNAL_SERVER_ERROR = 500
        val SERVICE_UNAVAILABLE = 503
    }
}