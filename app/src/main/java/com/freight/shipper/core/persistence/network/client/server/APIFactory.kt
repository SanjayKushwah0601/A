package com.freight.shipper.core.persistence.network.client.server

import com.freight.shipper.BuildConfig
import com.freight.shipper.core.persistence.network.client.UniversalConverter
import com.freight.shipper.core.persistence.network.interceptors.AuthHeaderInterceptor
import com.freight.shipper.core.persistence.network.interceptors.MeuralHeaderInterceptor
import com.freight.shipper.core.persistence.preference.LoginManager
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.*
import java.util.concurrent.TimeUnit


object APIFactory {
    fun standardClient(loginManager: LoginManager): API {
        val authInterceptor = AuthHeaderInterceptor(loginManager)
        val headerInterceptor = MeuralHeaderInterceptor()

        val loggingInterceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            loggingInterceptor.level = HttpLoggingInterceptor.Level.NONE
        }

        val interceptors = ArrayList<Interceptor>(3)
        interceptors.add(authInterceptor)
        interceptors.add(headerInterceptor)
        interceptors.add(loggingInterceptor)
        return createClient(interceptors, BuildConfig.SERVER_URL, 10)
    }

    fun createClient(interceptors: List<Interceptor>, baseUrl: String, timeoutSeconds: Long): API {
        val okClientBuilder = OkHttpClient.Builder()

        interceptors.forEach {
            okClientBuilder.addInterceptor(it)
        }

        val okClient = okClientBuilder
            .connectTimeout(timeoutSeconds, TimeUnit.SECONDS)
            .writeTimeout(timeoutSeconds, TimeUnit.SECONDS)
            .readTimeout(timeoutSeconds, TimeUnit.SECONDS)
            .build()


        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(UniversalConverter())
            .client(okClient)
            .build()

        return API(retrofit)
    }
}