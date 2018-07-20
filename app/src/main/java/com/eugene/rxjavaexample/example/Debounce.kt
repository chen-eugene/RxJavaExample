package com.eugene.rxjavaexample.example

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.eugene.rxjavaexample.R
import com.eugene.rxjavaexample.rxview.RxView
import kotlinx.android.synthetic.main.activity_debounce.*
import java.util.concurrent.TimeUnit

class Debounce : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_debounce)

        debounce()
    }


    private fun debounce() {

        RxView.textChange(et_input)
                .debounce(2, TimeUnit.SECONDS)
                .skip(1)
                .subscribe {

                    tv_output.text = "发送给服务器的数据：$it"

                }
    }


}