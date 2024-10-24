package com.example.rescuedanimals.data.util

import com.example.rescuedanimals.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import java.net.URLDecoder

object PublicSrvcParamInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val url = originalRequest.url.newBuilder().apply {
            addQueryParameter("serviceKey", URLDecoder.decode(BuildConfig.PUBLIC_SRVC_KEY, "UTF-8"))
            addQueryParameter("_type", "json")
        }.build()

        val request = originalRequest.newBuilder().url(url).build()
        return chain.proceed(request)
    }
}