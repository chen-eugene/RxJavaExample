package com.eugene.rxjavaexample

import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.eugene.rxjavaexample.http.RetrofitApi
import com.eugene.rxjavaexample.http.RetrofitRepositoryManagerImpl
import com.eugene.rxjavaexample.utils.RxUtil
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.net.URI

class MainActivity : AppCompatActivity() {

    private val MULTIPART_FORM_DATA = "multipart/form-data"

    private val desc = "hello, this is description speaking"

    private var filePath = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    /**
     * 上传文件
     */
    private fun uploadFile() {

        val descBody = createPartFromString(desc)

        val filePart = createPartFromFile("image", filePath)

        val service = RetrofitRepositoryManagerImpl.obtainRetrofitService(RetrofitApi::class.java)

        val observable = service.uploadFile(descBody, filePart)
        observable.compose(RxUtil.applySchedulers())
                .subscribe {
                    print(it.msg)
                }

    }

    private fun createPartFromString(str: String): RequestBody {
        return RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), str)
    }

    private fun createPartFromFile(partName: String, filePath: String): MultipartBody.Part {
        val file = File(filePath)

        // 为file建立RequestBody实例
        val requestBody = RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), file)

        // MultipartBody.Part借助文件名完成最终的上传
        return MultipartBody.Part.createFormData(partName, file.name, requestBody)
    }


}
