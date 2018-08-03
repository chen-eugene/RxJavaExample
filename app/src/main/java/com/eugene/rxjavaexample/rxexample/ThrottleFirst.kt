package com.eugene.rxjavaexample.rxexample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.eugene.rxjavaexample.R
import com.eugene.rxjavaexample.rxview.RxView
import kotlinx.android.synthetic.main.activity_throttle_first.*
import java.util.concurrent.TimeUnit

/**
 * 功能防抖
 */
class ThrottleFirst : AppCompatActivity() {

    private val TAG = this::class.java.name

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_throttle_first)

        throttleFirst()

    }


    private fun throttleFirst() {

        RxView.clicks(btn_click)
                .throttleFirst(2, TimeUnit.SECONDS)  // 才发送 2s内第1次点击按钮的事件
                .subscribe {

                    Log.d(TAG, "功能防抖")

                }

    }

}