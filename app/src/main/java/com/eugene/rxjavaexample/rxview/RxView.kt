package com.eugene.rxjavaexample.rxview

import android.view.View
import android.widget.TextView
import io.reactivex.Observable

object RxView {

    fun clicks(view: View): Observable<String> {
        return ViewClickObservable(view)
    }

    fun textChange(view: TextView): Observable<String> {
        return TextChangeObservable(view)
    }

}