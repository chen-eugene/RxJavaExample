package com.eugene.rxjavaexample.operator

import io.reactivex.Observable

class Combine {

    /**
     * 合并多个事件源，串行发送
     */
    fun concat() {

        val observable01 = Observable.just(1, 2, 3)
        val observable02 = Observable.just(4, 5, 6)
        val observable03 = Observable.just(7, 8, 9)

        Observable.concat(observable01, observable02, observable03)
                .subscribe {
                    println("合并后的序列：$it")
                }

    }

    /**
     * 合并多个事件源，并行发送
     */
    fun merge(){

        val observable01 = Observable.just(1, 2, 3)
        val observable02 = Observable.just(4, 5, 6)
        val observable03 = Observable.just(7, 8, 9)

        Observable.merge(observable01,observable02,observable03)
                .subscribe {
                    println("合并后的序列：$it")
                }

    }


}