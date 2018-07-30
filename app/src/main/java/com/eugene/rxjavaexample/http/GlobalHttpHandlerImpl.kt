package com.eugene.rxjavaexample.http

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response


class GlobalHttpHandlerImpl : HttpHandler {

    /**
     *  这里可以在请求服务器之前可以拿到request,做一些操作比如给request统一添加token或者header以及参数加密等操作
     */
    override fun onHttpRequestBefore(chain: Interceptor.Chain, request: Request): Request {
        return request
    }


    override fun onHttpResultResponse(chain: Interceptor.Chain, response: Response): Response {
        return response
    }
}
