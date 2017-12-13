package com.kanziw.androidstart

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.bottom_tab_layout.*

class MainActivity : AppCompatActivity() {

    private val disposeBag = CompositeDisposable()
    private lateinit var counterFragment: CounterFragment
    private var todoListFragment: ToDoListFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        counterFragment = CounterFragment.newInstance()

        supportFragmentManager.beginTransaction()
                .add(R.id.main_view, counterFragment)
                .commit()

        RxView.clicks(bottom_tab_1)
                .subscribe {
                    val ft = supportFragmentManager.beginTransaction()

                    if (counterFragment.isHidden) {
                        ft.show(counterFragment)
                    }

                    if (todoListFragment?.isAdded == true) {
                        ft.hide(todoListFragment)
                    }

                    ft.commit()
                }
                .addTo(disposeBag)

        RxView.clicks(bottom_tab_2)
                .subscribe {
                    val ft = supportFragmentManager.beginTransaction()

                    if (todoListFragment == null) {
                        todoListFragment = ToDoListFragment.newInstance()
                    }

                    if (todoListFragment?.isAdded == false) {
                        ft.add(R.id.main_view, todoListFragment)
                    } else {
                        ft.show(todoListFragment)
                    }

                    if (counterFragment.isAdded) {
                        ft.hide(counterFragment)
                    }

                    ft.commit()
                }
                .addTo(disposeBag)
    }

    override fun onDestroy() {
        disposeBag.dispose()
        super.onDestroy()
    }
}
