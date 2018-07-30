package com.eugene.rxjavaexample

import com.eugene.rxjavaexample.operator.Transform
import org.junit.Test

class TransformTest {

    private val transform by lazy {
        Transform()
    }


    @Test
    fun map() {
        transform.map()
    }

    @Test
    fun flatMap() {
        transform.flatMap()
    }

    @Test
    fun concatMap() {
        transform.concatMap()
    }


    @Test
    fun buffer() {
        transform.buffer()
    }


}