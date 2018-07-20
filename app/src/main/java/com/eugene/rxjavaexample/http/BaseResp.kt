package com.eugene.rxjavaexample.http

data class BaseResp<T>(
        val status: Int,
        val data: T,
        val msg: String
)