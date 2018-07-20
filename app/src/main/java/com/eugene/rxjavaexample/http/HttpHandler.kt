/*
 * Copyright 2017 JessYan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.eugene.core.module.http

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
