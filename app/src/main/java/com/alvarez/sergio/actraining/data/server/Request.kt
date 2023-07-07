package com.alvarez.sergio.actraining.okhttp

import com.alvarez.sergio.actraining.domain.exception
import com.alvarez.sergio.actraining.domain.exceptionIO
import okhttp3.OkHttpClient
import okhttp3.Request
import java.net.SocketTimeoutException
import java.net.URL
import java.util.concurrent.TimeUnit

var client: OkHttpClient = OkHttpClient()
const val TIME_OUT = 25L

fun getRequest(url: String): String? {
    client = OkHttpClient.Builder()
        .callTimeout(TIME_OUT, TimeUnit.SECONDS)
        .build()

    val result: String?

    try {
        val urlRetreived = URL(url)
        val request = Request.Builder().url(urlRetreived).build()

        val response = client.newCall(request).execute()
        // TODO TimeOut and all the call's definition params
        result = response.body?.string()
    } catch (error: Error) {
        throw exception("General request error")
    } catch (errorOnRequest: SocketTimeoutException) {
        throw exceptionIO("Error timeout")
    }
    return result
}
