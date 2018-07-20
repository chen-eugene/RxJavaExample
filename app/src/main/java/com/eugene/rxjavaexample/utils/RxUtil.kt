package com.eugene.rxjavaexample.utils

import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

object RxUtil {

    private val schedulersTransformer by lazy {
        ObservableTransformer<Any, Any> {
            it.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }
    }

    fun <T> applySchedulers(): ObservableTransformer<T, T> {
        return schedulersTransformer as ObservableTransformer<T,T>
    }


}