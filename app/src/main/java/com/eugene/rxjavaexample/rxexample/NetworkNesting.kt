package com.eugene.rxjavaexample.rxexample

import android.annotation.SuppressLint
import android.util.Log
import com.eugene.rxjavaexample.Api
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * 网络嵌套
 */
class NetworkNesting {

    private val TAG = NetworkPollingConditional::class.java.name

    @SuppressLint("CheckResult")
    fun requestData() {

        // 步骤1：创建Retrofit对象
        val retrofit = Retrofit.Builder()
                .baseUrl("http://fy.iciba.com/") // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 支持RxJava
                .build()

        // 步骤2：创建 网络请求接口 的实例
        val request = retrofit.create(Api::class.java)

        // 步骤3：采用Observable<...>形式 对 2个网络请求 进行封装
        val observable1 = request.register()
        val observable2 = request.login()

        observable1.subscribeOn(Schedulers.io())               // （初始被观察者）切换到IO线程进行网络请求1
                .observeOn(AndroidSchedulers.mainThread())  // （新观察者）切换到主线程 处理网络请求1的结果
                .doOnNext {
                    Log.d(TAG, "第1次网络请求成功")
                    Log.d(TAG, it.toString())
                }
                .observeOn(Schedulers.io())                 // （新被观察者，同时也是新观察者）切换到IO线程去发起登录请求
                // 特别注意：因为flatMap是对初始被观察者作变换，所以对于旧被观察者，它是新观察者，所以通过observeOn切换线程
                // 但对于初始观察者，它则是新的被观察者
                .flatMap {
                    // 作变换，即作嵌套网络请求
                    // 将网络请求1转换成网络请求2，即发送网络请求2
                    observable2
                }
                .observeOn(AndroidSchedulers.mainThread())  // （初始观察者）切换到主线程 处理网络请求2的结果
                .subscribe({
                    Log.d(TAG, "第2次网络请求成功")
                    Log.d(TAG, it.toString())
                    // 对第2次网络请求返回的结果进行操作 = 显示翻译结果
                }, {
                    Log.d(TAG, "登录失败")
                })

    }


}