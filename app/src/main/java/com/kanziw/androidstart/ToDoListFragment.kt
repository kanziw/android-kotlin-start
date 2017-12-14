package com.kanziw.androidstart

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_todolist.*

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
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = ToDoListAdapter(context, getLists())
    }

    private fun getLists() = arrayListOf("JAVA", "KOTLIN", "PHP", "SWIFT", "JAVA Script", "MYSQL")

    companion object {
        fun newInstance() = ToDoListFragment()
    }
}
