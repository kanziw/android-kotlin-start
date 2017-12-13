package com.kanziw.androidstart

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.reactivex.disposables.CompositeDisposable

class ToDoListFragment : Fragment() {

    private val disposeBag = CompositeDisposable()

    override fun onDestroy() {
        disposeBag.dispose()
        super.onDestroy()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater?.inflate(R.layout.fragment_todolist, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        println(" TO-DO LIST Fragment Appeared!")
    }

    companion object {
        fun newInstance() = ToDoListFragment()
    }
}
