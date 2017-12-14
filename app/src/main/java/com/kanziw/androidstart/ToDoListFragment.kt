package com.kanziw.androidstart

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.fragment_todolist.*

class ToDoListFragment : Fragment() {

    private val disposeBag = CompositeDisposable()
    private val toDoTextList = ArrayList<String>()

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
        recyclerView.adapter = ToDoListAdapter(context, toDoTextList)

        RxView.clicks(button_save)
                .map { edit_text.text }
                .filter { it.isNotEmpty() }
                .subscribe {
                    toDoTextList.add(it.toString())
                    edit_text.text.clear()
                    recyclerView.adapter.notifyDataSetChanged()
                }
                .addTo(disposeBag)

        recyclerView.setOnTouchListener { _, motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_UP) edit_text.hideKeyboard()
            false
        }
    }

    private fun View.hideKeyboard() {
        val imm = context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

    companion object {
        fun newInstance() = ToDoListFragment()
    }
}
