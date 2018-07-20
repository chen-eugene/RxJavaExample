package com.eugene.rxjavaexample.http

import com.eugene.rxjavaexample.bean.LoginBean
import com.eugene.rxjavaexample.bean.RequestParams
import com.eugene.rxjavaexample.bean.WorldBean
import io.reactivex.Observable
import retrofit2.http.*

interface RetrofitApi {

    /**
     * GET请求不带参数
     *
     * http://fy.iciba.com/ajax.php?a=fy&f=auto&t=auto&w=hi%20world
     */
    @GET("ajax.php?a=fy&f=auto&t=auto&w=hi%20world")
    fun getWorld(): Observable<WorldBean>

    /**
     * GET请求@Query
     *
     * http://fy.iciba.com/ajax.php?a=fy&f=auto&t=auto&w=hi%20world
     */
    @GET("ajax.php")
    fun getWorldQuery(@Query("a") a: String, @Query("f") f: String,
                      @Query("t") t: String, @Query("w") w: String): Observable<WorldBean>

    /**
     * GET请求@QueryMap
     *
     * http://fy.iciba.com/ajax.php?a=fy&f=auto&t=auto&w=hi%20world
     */
    @GET("ajax.php")
    fun getWorldQueryMap(@QueryMap map: Map<String, Any>): Observable<WorldBean>

    /**
     * GET请求@QueryMap
     * 假如你需要添加相同Key值，但是value却有多个的情况，一种方式是添加多个@Query参数，
     * 还有一种简便的方式是将所有的value放置在列表中，然后在同一个@Query下完成添加
     *
     * http://fy.iciba.com/ajax.php?a=fy&a=auto&a=auto&a=hi%20world
     */
    @GET("ajax.php")
    fun getWorldQueryList(@Query("a") list: List<String>): Observable<WorldBean>

    /**
     * GET请求@Path
     * 如果请求的相对地址也是需要调用方传递，那么可以使用@Path注解
     *
     * http://fy.iciba.com/ajax.php?a=fy&f=auto&t=auto&w=hi%20world
     */
    @GET("{ajax}.php?a=fy&f=auto&t=auto&w=hi%20world")
    fun getWorldQueryPath(@Path("ajax") ajax: String): Observable<WorldBean>

    /**
     * POST请求@Field
     *
     * http://fy.iciba.com/ajax.php?a=fy&f=auto&t=auto&w=hi%20world
     */
    @FormUrlEncoded
    @POST("ajax.php")
    fun postWorldField(@Field("a") a: String, @Field("f") f: String,
                       @Field("t") t: String, @Field("w") w: String): Observable<WorldBean>

    /**
     * POST请求@Field
     *
     * http://fy.iciba.com/ajax.php?a=fy&f=auto&t=auto&w=hi%20world
     */
    @FormUrlEncoded
    @POST("ajax.php")
    fun postWorldFieldMap(@FieldMap map: Map<String, String>): Observable<WorldBean>


    /**
     * POST请求@Body
     * 如果Post请求参数有多个，那么统一封装到类中应该会更好，
     * 除了使用实体类，也可以使用Map
     * http://fy.iciba.com/ajax.php?a=fy&f=auto&t=auto&w=hi%20world
     */
    @FormUrlEncoded
    @POST("ajax.php")
    fun postWorldBody(@Body params: RequestParams): Observable<WorldBean>






}