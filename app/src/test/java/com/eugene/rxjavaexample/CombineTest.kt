package com.eugene.rxjavaexample

import com.eugene.rxjavaexample.operator.Combine
import org.junit.Test

class CombineTest {

    private val combine by lazy {
        Combine()
    }

    @Test
    fun concat() {
        combine.concat()
    }

    @Test
    fun merge() {
        combine.merge()
    }


}