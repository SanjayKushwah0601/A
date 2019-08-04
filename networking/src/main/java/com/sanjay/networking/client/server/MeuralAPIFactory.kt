package com.sanjay.networking.client.server

import com.sanjay.networking.client.UniversalConverter
import com.sanjay.networking.interceptors.AuthHeaderInterceptor
import com.sanjay.networking.interceptors.MeuralHeaderInterceptor
import com.sanjay.networking.persistence.LoginManager
import com.sanjay.networking.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.*
import java.util.concurrent.TimeUnit


object MeuralAPIFactory {
    fun standardClient(loginManager: LoginManager): MeuralAPI {
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

    fun createClient(interceptors: List<Interceptor>, baseUrl: String, timeoutSeconds: Long): MeuralAPI {
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

        return MeuralAPI(retrofit)
    }
}