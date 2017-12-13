package com.kanziw.androidstart

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.fragment_counter.*

class CounterFragment : Fragment() {

    private val disposeBag = CompositeDisposable()

    override fun onDestroy() {
        disposeBag.dispose()
        super.onDestroy()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater?.inflate(R.layout.fragment_counter, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val streamInc = RxView.clicks(button_inc).map { 1 }
        val streamDec = RxView.clicks(button_dec).map { -1 }

        Observable.merge(streamInc, streamDec)
                .scan(0, { oldValue, newValue -> oldValue + newValue })
                .map { it.toString() }
                .subscribe(RxTextView.text(main_text))
                .addTo(disposeBag)
    }

    companion object {
        fun newInstance() = CounterFragment()
    }
}
