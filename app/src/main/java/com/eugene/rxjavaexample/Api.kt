package com.eugene.rxjavaexample

import com.eugene.rxjavaexample.bean.ChinaBean
import com.eugene.rxjavaexample.bean.LoginBean
import com.eugene.rxjavaexample.bean.WorldBean
import com.eugene.rxjavaexample.bean.RegisterBean
import io.reactivex.Observable
import retrofit2.http.GET

interface Api {

    @GET("ajax.php?a=fy&f=auto&t=auto&w=hi%20world")
    fun getWorld(): Observable<WorldBean>

    // 网络请求2
    @GET("ajax.php?a=fy&f=auto&t=auto&w=hi%20china")
    fun getChina(): Observable<ChinaBean>

    // 网络请求1
    @GET("ajax.php?a=fy&f=auto&t=auto&w=hi%20register")
    fun register(): Observable<RegisterBean>

    // 网络请求2
    @GET("ajax.php?a=fy&f=auto&t=auto&w=hi%20login")
    fun login(): Observable<LoginBean>






}