package com.eugene.rxjavaexample.example

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.eugene.rxjavaexample.R
import com.eugene.rxjavaexample.rxview.RxView
import io.reactivex.Observable
import io.reactivex.functions.Function3
import kotlinx.android.synthetic.main.activity_combine_latest.*

/**
 * 联合判断多个事件
 */
class CombineLatest : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_combine_latest)

        combline()
    }

    fun combline() {

        //采用skip(1)原因：跳过 一开始EditText无任何输入时的空值
        val nameObservable = RxView.textChange(et_name).skip(1)
        val ageObservable = RxView.textChange(et_age).skip(1)
        val jobObservable = RxView.textChange(et_job).skip(1)

        Observable.combineLatest(nameObservable, ageObservable, jobObservable,
                Function3<String, String, String, Boolean> { t1, t2, t3 ->

                    val isNameEmpty = et_name.text.toString().isEmpty()
                    val isAgeEmpty = et_age.text.toString().isEmpty()
                    val isJobEmpty = et_job.text.toString().isEmpty()

                    //返回信息 = 联合判断，即3个信息同时已填写，"提交按钮"才可点击
                    isNameEmpty && isAgeEmpty && isJobEmpty
                })
                .subscribe {
                    btn_submit.isEnabled = it
                }

    }

}