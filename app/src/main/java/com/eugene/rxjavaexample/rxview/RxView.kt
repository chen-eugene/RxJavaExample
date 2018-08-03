package com.eugene.rxjavaexample.rxview

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.TextView
import io.reactivex.Observable
import io.reactivex.Observer

object RxView {

    fun clicks(view: View?): Observable<String> {
        return ViewClickObservable(view)
    }

    fun textChange(view: TextView?): Observable<String> {
        return TextChangeObservable(view)
    }

}