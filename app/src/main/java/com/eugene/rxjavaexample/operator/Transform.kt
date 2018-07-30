package com.eugene.rxjavaexample.operator

import io.reactivex.Observable

/**
 * 变换操作符
 */
class Transform {

    /**
     * 将发送的事件转换为任意的类型事件。
     */
    fun map() {

        Observable.create<Int> {

            it.onNext(1)
            it.onNext(2)
            it.onNext(3)

        }.map {

            "map将Int $it 转换为String $it"

        }.subscribe {

            println(it)

        }

    }

    /**
     * 将被观察者发送的事件序列进行 拆分 & 单独转换，再合并成一个新的事件序列，最后再进行发送
     */
    fun flatMap() {

        Observable.just(1, 2, 3)
                .flatMap {
                    //返回一个新的Obsrvable
                    Observable.just("map将Int $it 转换为String $it")
                }.subscribe {
                    println(it)
                }

    }

    /**
     * 拆分 & 重新合并生成的事件序列 的顺序 = 被观察者旧序列生产的顺序
     */
    fun concatMap() {

        Observable.fromArray(1, 2, 3, 4)
                .concatMap {
                    //返回一个新的Obsrvable
                    Observable.just("map将Int $it 转换为String $it")
                }.subscribe {
                    println(it)
                }

    }

    /**
     * buffer将数据缓存到一个集合当中，然后在适当的时机一起发送。
     * 注意：如果原来的Observable发射了一个onError通知，Buffer会立即传递这个通知，
     * 而不是首先发射缓存的数据，即使在这之前缓存中包含了原始Observable发射的数据。
     */
    fun buffer() {

        /**
         * @param count 缓存区的大小
         * @param skip 跳过地几个数据，然后开始重新获取新数据
         *             skip = 1 输出：123 234 345 456 56 6
         *             skip = 3 输出：123 456
         *             skip = 4 输出：123 56
         */
        Observable.just(1, 2, 3, 4, 5, 6)
                .buffer(3, 4)
                .subscribe {
                    val size = it.size
                    println("缓冲的数量：$size")
                    it.forEach {
                        println(it)
                    }
                }

    }


}