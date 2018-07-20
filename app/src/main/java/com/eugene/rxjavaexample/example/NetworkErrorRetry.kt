package com.eugene.rxjavaexample.example

import android.util.Log
import com.eugene.rxjavaexample.Api
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class NetworkErrorRetry {

    private val TAG = this::class.java.name

    private val maxConnectCount = 3
    // 当前已重试次数
    private var curRetryCount = 0
    // 重试等待时间
    private val waitRetryTime = 2L


    fun request() {

        val retrofit = Retrofit.Builder()
                .baseUrl("http://fy.iciba.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

        val request = retrofit.create(Api::class.java)

        request.getWorld()
                .retryWhen {
                    it.flatMap {
                        if (curRetryCount++ < maxConnectCount) {
                            Log.d(TAG, "当前重连次数$curRetryCount")
                            Observable.timer(waitRetryTime, TimeUnit.SECONDS)
                        } else {
                            Log.d(TAG, "当前重连次数$curRetryCount，不用再重连")
                            Observable.error(it)
                        }
                    }
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {

                    it.toString()

                }


    }


}