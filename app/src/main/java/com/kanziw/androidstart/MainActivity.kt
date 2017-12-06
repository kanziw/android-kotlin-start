package com.kanziw.androidstart

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var disposeBag: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        disposeBag = RxView.clicks(button).subscribe({ println("") })
    }

    override fun onDestroy() {
        disposeBag?.dispose()
        super.onDestroy()
    }
}
