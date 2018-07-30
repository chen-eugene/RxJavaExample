package com.eugene.rxjavaexample

import com.eugene.rxjavaexample.operator.Creator
import org.junit.Test

class CreatorTest {

    val creator by lazy {
        Creator()
    }


    @Test
    fun create() {
        creator.create()
    }

    @Test
    fun defer() {
        creator.defer()
    }

    @Test
    fun timer() {
        creator.timer()
    }

    @Test
    fun interval() {
        creator.interval()
    }

    @Test
    fun intervalRange() {
        creator.intervalRange()
    }

    @Test
    fun range() {
        creator.range()
    }


}