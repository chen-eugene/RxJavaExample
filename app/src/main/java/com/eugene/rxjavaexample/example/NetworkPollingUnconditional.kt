package com.eugene.rxjavaexample.example

import android.util.Log
import com.eugene.rxjavaexample.Api
import com.eugene.rxjavaexample.bean.WorldBean
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * 网络无条件轮询
 */
class NetworkPollingUnconditional {

    private val TAG = NetworkPollingUnconditional::class.java.name

    fun requestData() {
        /*
         * 步骤1：采用interval（）延迟发送
         * 注：此处主要展示无限次轮询，若要实现有限次轮询，仅需将interval（）改成intervalRange（）即可
         **/
        Observable.interval(2, 1, TimeUnit.SECONDS)
                // 参数说明：
                // 参数1 = 第1次延迟时间；
                // 参数2 = 间隔时间数字；
                // 参数3 = 时间单位；
                // 该例子发送的事件特点：延迟2s后发送事件，每隔1秒产生1个数字（从0开始递增1，无限个）

                /*
                 * 步骤2：每次发送数字前发送1次网络请求（doOnNext（）在执行Next事件前调用）
                 * 即每隔1秒产生1个数字前，就发送1次网络请求，从而实现轮询需求
                 **/
                .doOnNext({

                    /*
                     * 步骤3：通过Retrofit发送网络请求
                     **/
                    // a. 创建Retrofit对象
                    val retrofit = Retrofit.Builder()
                            .baseUrl("http://fy.iciba.com/") // 设置 网络请求 Url
                            .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 支持RxJava
                            .build()

                    // b. 创建 网络请求接口 的实例
                    val request = retrofit.create(Api::class.java)


                    // c. 采用Observable<...>形式 对 网络请求 进行封装
                    val observable = request.getWorld()
                    // d. 通过线程切换发送网络请求
                    observable.subscribeOn(Schedulers.io())               // 切换到IO线程进行网络请求
                            .observeOn(AndroidSchedulers.mainThread())  // 切换回到主线程 处理请求结果
                            .subscribe(object : Observer<WorldBean> {
                                override fun onComplete() {
                                }

                                override fun onSubscribe(d: Disposable) {
                                }

                                override fun onNext(t: WorldBean) {
                                    Log.e(TAG, t.toString())
                                }

                                override fun onError(e: Throwable) {
                                    Log.e(TAG, "请求失败")
                                }
                            })

                }).subscribe(object : Observer<Long> {
                    override fun onComplete() {
                        Log.e(TAG, "对Complete事件作出响应")
                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(t: Long) {
                    }

                    override fun onError(e: Throwable) {
                        Log.e(TAG, "对Error事件作出响应")
                    }
                })
    }


}