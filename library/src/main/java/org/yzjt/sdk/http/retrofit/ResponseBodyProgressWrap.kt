package org.yzjt.sdk.http.retrofit

import okhttp3.MediaType
import okhttp3.ResponseBody
import okio.*
import org.yzjt.sdk.entity.DownloadEntity
import org.yzjt.sdk.rx.RxBus
import java.io.IOException

/**
 * Created by LT on 2019/7/31.
 */
class ResponseBodyProgressWrap : ResponseBody {

    private var responseBody:ResponseBody
    private var bufferedSource:BufferedSource? = null
    private var tag:String = Thread.currentThread().name

    constructor(responseBody: ResponseBody) : super() {
        this.responseBody = responseBody
    }

    constructor(responseBody: ResponseBody, tag: String) : super() {
        this.responseBody = responseBody
        this.tag = tag
    }


    override fun contentLength(): Long {
        return responseBody.contentLength()
    }

    override fun contentType(): MediaType? {
        return responseBody.contentType()
    }

    override fun source(): BufferedSource? {
        if (bufferedSource == null) {
            bufferedSource = Okio.buffer(source(responseBody.source()))
        }
        return bufferedSource
    }

    private fun source(source: Source): Source {
        return object : ForwardingSource(source) {
            private var bytesReaded: Long = 0
            @Throws(IOException::class)
            override fun read(sink: Buffer, byteCount: Long): Long {
                val bytesRead = super.read(sink, byteCount)
                bytesReaded += if (bytesRead == -1L) {
                    0 //使用RxBus的方式，实时发送当前已读取(上传/下载)的字节数据
                } else {
                    bytesRead
                }
                RxBus.getDefault().post(DownloadEntity(contentLength(), bytesReaded, tag))
                return bytesRead
            }
        }
    }

}