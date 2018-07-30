package com.eugene.rxjavaexample.http

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * ================================================
 * 处理 Http 请求和响应结果的处理类
 *
 * @see [HttpHandler Wiki 官方文档](https://github.com/JessYanCoding/MVPArms/wiki.3.2)
 * Created by JessYan on 8/30/16 17:47
 * [Contact me](mailto:jess.yan.effort@gmail.com)
 * [Follow me](https://github.com/JessYanCoding)
 * ================================================
 */
interface HttpHandler {
    fun onHttpResultResponse(chain: Interceptor.Chain, response: Response): Response

    fun onHttpRequestBefore(chain: Interceptor.Chain, request: Request): Request

    companion object {

        //空实现
        val EMPTY: HttpHandler = object : HttpHandler {
            override fun onHttpResultResponse(chain: Interceptor.Chain, response: Response): Response {
                //不管是否处理,都必须将response返回出去
                return response
            }

            override fun onHttpRequestBefore(chain: Interceptor.Chain, request: Request): Request {
                //不管是否处理,都必须将request返回出去
                return request
            }
        }
    }

}
