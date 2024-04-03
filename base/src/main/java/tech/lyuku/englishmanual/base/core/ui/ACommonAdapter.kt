package tech.lyuku.englishmanual.base.core.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

abstract class ACommonAdapter<T : Any, VH : ACommonViewHolder<T>> :
    ListAdapter<T, VH>(CommonDiffCallback()) {

    private var inflater: LayoutInflater? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        if (inflater == null) {
            inflater = LayoutInflater.from(parent.context)
        }
        return createViewHolder(inflater!!, parent)
    }

    abstract fun createViewHolder(inflater: LayoutInflater, parent: ViewGroup): VH

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(getItem(position))
    }
}

abstract class ACommonViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {

    abstract fun bind(item: T)
}


class CommonDiffCallback<T : Any> : DiffUtil.ItemCallback<T>() {

    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem === newItem
    }

    @Suppress("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem == newItem
    }

}

