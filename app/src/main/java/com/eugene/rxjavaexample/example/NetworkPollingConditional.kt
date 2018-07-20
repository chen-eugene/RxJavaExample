package com.eugene.rxjavaexample.example

import android.util.Log
import com.eugene.rxjavaexample.Api
import com.eugene.rxjavaexample.bean.WorldBean
import com.eugene.rxjavaexample.utils.RxUtil
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * 网络条件轮询
 */
class NetworkPollingConditional {

    private val TAG = NetworkPollingConditional::class.java.name
    // 设置变量 = 模拟轮询服务器次数
    private var i = 0

    fun requestData() {
        val retrofit = Retrofit.Builder()
                .baseUrl("http://fy.iciba.com/") // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 支持RxJava
                .build()

        val request = retrofit.create(Api::class.java)


        // 步骤4：发送网络请求 & 通过repeatWhen（）进行轮询
        request.getWorld()
                .compose(RxUtil.applySchedulers())
                .repeatWhen {
            it.flatMap({
                if (i > 3) {
                    // 此处选择发送onError事件以结束轮询，因为可触发下游观察者的onError（）方法回调
                    Observable.error<Throwable>(Throwable(""))
                }
                // 若轮询次数＜4次，则发送1Next事件以继续轮询
                // 注：此处加入了delay操作符，作用 = 延迟一段时间发送（此处设置 = 2s），以实现轮询间间隔设置
                Observable.just(1).delay(2000, TimeUnit.MILLISECONDS)
            })

        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())  // 切换回到主线程 处理请求结果
                .subscribe(object : Observer<WorldBean> {
                    override fun onComplete() {
                        Log.e(TAG, "对Complete事件作出响应")
                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(t: WorldBean) {
                        t.toString()
                        i++
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                    }

                })

    }

}