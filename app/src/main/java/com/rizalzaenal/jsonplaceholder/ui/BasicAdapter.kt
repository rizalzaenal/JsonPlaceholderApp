package com.rizalzaenal.jsonplaceholder.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BasicAdapter<T, V : ViewBinding>()
    : RecyclerView.Adapter<BasicAdapter.BasicViewHolder<V>>() {
    private val data: ArrayList<T> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasicViewHolder<V> {
        return BasicViewHolder(inflateBinding(parent))
    }

    override fun onBindViewHolder(holder: BasicViewHolder<V>, position: Int) {
        bind(data = data[position], holder.binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    abstract fun inflateBinding(parent: ViewGroup): V
    abstract fun bind(data: T, binding: V)

    fun setItems(list: List<T>) {
        data.clear()
        data.addAll(list)
        notifyDataSetChanged()
    }


    class BasicViewHolder<V : ViewBinding>(val binding: V) :
        RecyclerView.ViewHolder(binding.root) {}
}