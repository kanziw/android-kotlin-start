package com.kanziw.androidstart

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var disposeBag = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val streamInc = RxView.clicks(button_inc).map { 1 }
        val streamDec = RxView.clicks(button_dec).map { -1 }

        Observable.merge(streamInc, streamDec)
                .scan(0, { oldValue, newValue -> oldValue + newValue })
                .map { it.toString() }
                .subscribe(RxTextView.text(main_text))
                .addTo(disposeBag)
    }

    override fun onDestroy() {
        disposeBag.dispose()
        super.onDestroy()
    }
}
