package com.kanziw.androidstart

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.bottom_tab_layout.*

class MainActivity : AppCompatActivity() {

    private val disposeBag = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
                .add(R.id.main_view, CounterFragment.newInstance())
                .commit()

        RxView.clicks(bottom_tab_1)
                .subscribe {
                    val fm = supportFragmentManager

                    val currentFragment = fm.findFragmentById(R.id.main_view)
                    if (currentFragment !is CounterFragment) {
                        fm.fragments.find { it is CounterFragment }?.let {
                            fm.beginTransaction().show(it).hide(currentFragment).commit()
                        } ?: run {
                            fm.beginTransaction().add(R.id.main_view, CounterFragment.newInstance()).hide(currentFragment).commit()
                        }
                    }
                }
                .addTo(disposeBag)

        RxView.clicks(bottom_tab_2)
                .subscribe {
                    val fm = supportFragmentManager

                    val currentFragment = fm.findFragmentById(R.id.main_view)
                    if (currentFragment !is ToDoListFragment) {
                        fm.fragments.find { it is ToDoListFragment }?.let {
                            fm.beginTransaction().show(it).hide(currentFragment).commit()
                        } ?: run {
                            fm.beginTransaction().add(R.id.main_view, ToDoListFragment.newInstance()).hide(currentFragment).commit()
                        }
                    }
                }
                .addTo(disposeBag)
    }

    override fun onDestroy() {
        disposeBag.dispose()
        super.onDestroy()
    }
}
