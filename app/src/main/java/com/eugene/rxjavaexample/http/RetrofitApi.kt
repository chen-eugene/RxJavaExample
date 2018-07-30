package com.eugene.rxjavaexample.http

import com.eugene.rxjavaexample.bean.RequestParams
import com.eugene.rxjavaexample.bean.WorldBean
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*


interface RetrofitApi {

    /**
     * 使用Retrofit2一般都是针对于一baseURL，
     * 其它接口都是拼接不同的参数如get/photo,search?name=xiaohong&&sex=female,这样的形式。
     * 但是一些请求此时又要访问不同的url只能重新生成一个Retrofit2实例，实质上还有一种形式去处理，就是使用@url注解
     *
     * @url 可以动态的设置url
     */
    fun dynamicUrl(@Url url: String)

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


    /**
     * 上传单个文件
     * @Part("description") 可以加一些描述信息(可以不加)
     * 描述信息可以放在RequestBody中，也可以直接用String
     * @Part file: MultipartBody.Part 文件必须使用MultipartBody上传
     */
    @Multipart
    @POST("upload")
    fun uploadFile(@Part("description") description: RequestBody,
                   @Part file: MultipartBody.Part): Observable<BaseResp<Nothing>>

    /**
     * 上传文件放到RequestBody中
     * 如果需要上传多个文件，可以增加@Part
     * @Part("desc") desc: String 添加一些描述信息(可以不加)
     */
    @Multipart
    @POST("upload")
    fun uploadFile(@Part("description") description: String,
                   @Part("file\"; filename=\"1.txt") file: RequestBody): Observable<BaseResp<Nothing>>

    /**
     * 上传多个文件，针对固定数量的文件
     */
    @Multipart
    @POST("upload")
    fun uoloadFiles(@Part("description") description: String,
                    @Part("file\"; filename=\"1.txt") file1: RequestBody,
                    @Part("file\"; filename=\"2.txt") file2: RequestBody): Observable<BaseResp<Nothing>>

    /**
     * 使用@PartMap
     * 上传多个文件，文件数量不确定
     */
    @Multipart
    @POST("upload")
    fun uoloadFiles(@Part("description") description: String
                    , @PartMap maps: Map<String, RequestBody>): Observable<BaseResp<Nothing>>

    /**
     * 使用List
     * 上传多个文件，文件数量不确定
     */
    @Multipart
    @POST("upload")
    fun uoloadFiles(@Part("description") description: String
                    , @Part parts: List<MultipartBody.Part>): Observable<BaseResp<Nothing>>

    /**
     * 不是使用@Multipart注解，
     * 直接使用@Body注解方法参数
     */
    @POST("upload")
    fun uploadFiles(@Body multipartBody: MultipartBody): Observable<BaseResp<Nothing>>

    /**
     * 文件和文字一起上传
     */
    fun uploadFileAndText(@FieldMap map: Map<String, String>,
                          @Part("file\"; filename=\"1.txt") file1: RequestBody): Observable<BaseResp<Nothing>>

}