package com.kanziw.androidstart

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Observable.just(0)
                .delay(1, TimeUnit.SECONDS)
                .subscribe {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
    }
}
