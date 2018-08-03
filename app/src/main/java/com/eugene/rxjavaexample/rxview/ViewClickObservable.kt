package com.eugene.rxjavaexample.rxview

import android.view.View
import io.reactivex.Observable
import io.reactivex.Observer

class ViewClickObservable(val view: View?) : Observable<String>() {

    override fun subscribeActual(observer: Observer<in String>?) {
        view?.setOnClickListener {
            observer?.onNext("onClick")
        }
    }


}