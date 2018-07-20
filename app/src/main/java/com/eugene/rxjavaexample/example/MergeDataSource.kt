package com.eugene.rxjavaexample.example

import android.util.Log
import com.eugene.rxjavaexample.Api
import com.eugene.rxjavaexample.bean.ChinaBean
import com.eugene.rxjavaexample.bean.WorldBean
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * 合并数据源
 */
class MergeDataSource {

    private val TAG = MergeDataSource::class.java.name

    fun request() {

        // 步骤1：创建Retrofit对象
        val retrofit = Retrofit.Builder()
                .baseUrl("http://fy.iciba.com/") // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 支持RxJava
                .build()

        // 步骤2：创建 网络请求接口 的实例
        val request = retrofit.create(Api::class.java)

        // 步骤3：采用Observable<...>形式 对 2个网络请求 进行封装
        val observable1 = request.getWorld().subscribeOn(Schedulers.io()); // 新开线程进行网络请求1
        val observable2 = request.getChina().subscribeOn(Schedulers.io());// 新开线程进行网络请求2
        // 即2个网络请求异步 & 同时发送

        // 步骤4：通过使用Zip（）对两个网络请求进行合并再发送
        Observable.zip(observable1, observable2,
                // 注：创建BiFunction对象传入的第3个参数 = 合并后数据的数据类型
                BiFunction<WorldBean, ChinaBean, String> { t1, t2 ->
                    t1.toString() + " & " + t2.toString()
                })
                .observeOn(AndroidSchedulers.mainThread()) // 在主线程接收 & 处理数据
                .subscribe({
                    // 成功返回数据时调用
                    // 结合显示2个网络请求的数据结果
                    Log.d(TAG, "最终接收到的数据是：$it")
                }, {
                    // 网络请求错误时调用
                    System.out.println("登录失败")
                })
    }


}