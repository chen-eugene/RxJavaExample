package com.eugene.rxjavaexample.operator

import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.ObservableSource
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import java.util.concurrent.Callable
import java.util.concurrent.TimeUnit

/**
 * 创建类操作符
 */
class Creator {

    /**
     * create操作符
     */
    fun create() {

        Observable.create(ObservableOnSubscribe<String> {
            it.onNext("onNext")
            //发送完成事件，此事件后订阅者将不再接受其他事件
            it.onComplete()
        }).subscribe(object : Observer<String> {

            /**
             * 创建连接时调用，可以做一些初始化等操作
             */
            override fun onSubscribe(d: Disposable) {
                println("创建连接onSubscribe")
            }

            override fun onNext(t: String) {
                println("接收到了事件$t")
            }

            override fun onError(e: Throwable) {
                println("接收到了事件onError")
            }

            override fun onComplete() {
                println("接收到了事件onComplete")
            }

        })

    }

    /**
     * 直到有订阅者（Observer）订阅时，才动态创建被订阅者对象（Observable）
     * 被订阅者和订阅者时一对一关系
     */
    fun defer() {

        val observable = Observable.defer(object : Callable<ObservableSource<Int>> {

            override fun call(): ObservableSource<Int> {
                return Observable.just(1, 2, 3)
            }

        })

        //只有当第一个订阅者执行完后才会去创建第二个被订阅者并建立连接，
        // 然后才开始给第二个订阅者发送事件消息

        //第一个订阅者
        observable.subscribe {
            println("第一个订阅者：$it")
        }

        //第二个订阅者
        observable.subscribe {
            println("第二个订阅者：$it")
        }

    }

    /**
     * 延迟指定时间后，发送1个数值0（Long类型）,一般用于检测
     */
    fun timer() {

        //延迟一秒钟发射0L
        Observable.timer(1, TimeUnit.SECONDS)
                .subscribe {
                    println("接收的数据：$it   类型为：${it::class.java.name}")
                }

    }

    /**
     * 每隔指定时间 发送从0开始、无限递增1的整数序列
     */
    fun interval() {

        /**
         * @param initialDelay 第一次事件延时时间
         * @param period 每次时间间隔时间
         * @param unit 时间单位
         */
        Observable.interval(3, 1, TimeUnit.SECONDS)
                .subscribe {
                    println("接收到的数据：$it   数据类型：${it::class.java.name}")
                }

    }

    /**
     * 每隔指定时间 发送从指定范围内递增的整数序列
     */
    fun intervalRange() {

        /**
         * @param start 递增序列起始
         * @param count 序列的个数
         * @param initialDelay 第一个事件的延迟时间
         * @param period 时间间隔
         * @param unit 时间单位
         */
        Observable.intervalRange(2, 5, 1, 1, TimeUnit.SECONDS)
                .subscribe {
                    println("接收到的数据：$it   数据类型：${it::class.java.name}")
                }

    }

    /**
     * 连续发送从指定范围内递增1的整数序列
     */
    fun range() {

        /**
         * @param start 起始数
         * @param count 事件数量
         */
        Observable.range(3, 5)
                .subscribe {
                    println("接收到的数据：$it   数据类型：${it::class.java.name}")
                }

    }


}