package com.eugene.rxjavaexample.http

import android.util.Log
import com.eugene.rxjavaexample.BuildConfig
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitRepositoryManagerImpl : IRepositoryManager {

    private val retrofitServiceCache = mutableMapOf<String, Any>()

    private val retrofit by lazy {
        Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(Gson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    private val client by lazy {
        val loggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message ->
            //打印retrofit日志
            Log.i("RetrofitLog", "retrofitBack = $message")
        })
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val builder = OkHttpClient.Builder()
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(TIME_OUT, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            builder .addInterceptor(loggingInterceptor)
        }

        val globalHandler = GlobalHttpHandlerImpl()
        builder.addInterceptor {
            //拦截request
            val originalResp = it.proceed(globalHandler.onHttpRequestBefore(it, it.request()))

            //拦截response
            globalHandler.onHttpResultResponse(it, originalResp)
        }
        builder.build()
    }

    override fun <T> obtainRetrofitService(service: Class<T>): T {
        var retrofitService = retrofitServiceCache[service.name]
        if (retrofitService == null) {
            retrofitService = retrofit.create(service)
            retrofitServiceCache[service.name] = retrofitService!!
        }
        return retrofitService as T
    }

    override fun <T> obtainCacheService(cache: Class<T>): T? {
        return null
    }

    override fun clearAllCache() {

    }

}