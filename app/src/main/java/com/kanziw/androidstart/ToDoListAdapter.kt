package com.kanziw.androidstart

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.item_todolist.view.*

class ToDoListAdapter(private val context: Context, private val lists: ArrayList<String>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val disposeBag = CompositeDisposable()

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder? {
        val v = LayoutInflater.from(context).inflate(R.layout.item_todolist, parent, false)
        return Item(v)
    }

    override fun getItemCount(): Int = lists.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        (holder as Item).bindData(lists[position], disposeBag)
    }

    class Item(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindData(_list: String, _disposeBag: CompositeDisposable) {
            itemView.textView.text = _list
            RxView.clicks(itemView.button_clear)
                    .subscribe { println(" button clear clicked! idx : ...??") }
                    .addTo(_disposeBag)
        }
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView?) {
        println(" onDetachedFromRecyclerView")
        disposeBag.dispose()
        super.onDetachedFromRecyclerView(recyclerView)
    }
}