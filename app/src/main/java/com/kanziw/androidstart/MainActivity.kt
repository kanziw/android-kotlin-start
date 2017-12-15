package com.kanziw.androidstart

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.bottom_tab_layout.*

class MainActivity : AppCompatActivity() {

    private val disposeBag = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val initialMainViewFragment = CounterFragment.newInstance()
        supportFragmentManager.beginTransaction()
                .add(R.id.main_view, initialMainViewFragment)
                .commit()

        attachBottomTabButton(bottom_tab_1, initialMainViewFragment)
        attachBottomTabButton(bottom_tab_2, ToDoListFragment.newInstance())
    }

    private fun attachBottomTabButton(view: View, fragment: Fragment) {
        RxView.clicks(view)
                .subscribe {
                    view.hideKeyboard()
                    val ft = supportFragmentManager
                    val transaction = ft.beginTransaction()

                    // Hide all fragments
                    ft.fragments.forEach { transaction.hide(it) }

                    // Show or Add fragment
                    if (fragment.isAdded) transaction.show(fragment) else transaction.add(R.id.main_view, fragment)
                    transaction.commit()
                }
                .addTo(disposeBag)
    }

    private fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

    override fun onDestroy() {
        disposeBag.dispose()
        super.onDestroy()
    }
}
