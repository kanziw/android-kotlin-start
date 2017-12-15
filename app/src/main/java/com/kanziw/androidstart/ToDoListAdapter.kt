package com.kanziw.androidstart

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.item_todolist.view.*

class ToDoListAdapter(private val context: Context, private val lists: ArrayList<String>, private val eventEmitter: PublishSubject<UIEvent>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val disposeBagPerViewBinding = hashMapOf<Int, CompositeDisposable>()

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder? {
        val v = LayoutInflater.from(context).inflate(R.layout.item_todolist, parent, false)
        return Item(v)
    }

    override fun getItemCount(): Int = lists.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, index: Int) {
        (holder as? Item)?.let { item ->
            val hash = item.itemView.hashCode()
            disposeBagPerViewBinding[hash]?.dispose()
            disposeBagPerViewBinding[hash] = CompositeDisposable()
            disposeBagPerViewBinding[hash]?.let { item.bindData(lists[index], index, eventEmitter, it) }
        }
    }

    class Item(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindData(_list: String, index: Int, eventEmitter: PublishSubject<UIEvent>, disposeBag: CompositeDisposable) {
            itemView.textView.text = _list
            RxView.clicks(itemView.button_clear)
                    .subscribe { eventEmitter.takeIf { !it.hasComplete() }?.onNext(UIEvent.RemoveItemEvent(index)) }
                    .addTo(disposeBag)
        }
    }
}
