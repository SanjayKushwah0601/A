package com.freight.shipper.core.persistence.network.interceptors

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class MeuralHeaderInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        request = request.newBuilder()
                .addHeader("x-meural-api-version", "2")
                .addHeader("x-meural-source-platform", "android")
                .build()

        return chain.proceed(request)
    }
}
