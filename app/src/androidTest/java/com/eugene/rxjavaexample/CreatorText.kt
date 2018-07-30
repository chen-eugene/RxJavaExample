package com.eugene.rxjavaexample

import android.support.test.runner.AndroidJUnit4
import com.eugene.rxjavaexample.operator.Creator
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CreatorText {

    val creator by lazy {
        Creator()
    }


    @Test
    fun create(){
        creator.create()
    }




}