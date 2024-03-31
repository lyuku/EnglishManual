package tech.lyuku.englishmanual.features.books.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import tech.lyuku.englishmanual.core.ui.ACommonAdapter
import tech.lyuku.englishmanual.core.ui.ACommonViewHolder
import tech.lyuku.englishmanual.databinding.ItemBookDetailActionBinding
import tech.lyuku.englishmanual.models.BookDetailAction

class BookDetailActionAdapter(private val onActionClick: (action: BookDetailAction) -> Unit) :
    ACommonAdapter<BookDetailAction, BookDetailActionAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemBookDetailActionBinding) :
        ACommonViewHolder<BookDetailAction>(binding.root) {

        init {
            binding.root.setOnClickListener {
                onActionClick(getItem(layoutPosition))
            }
        }

        override fun bind(item: BookDetailAction) {
            binding.action = item
        }
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).id.toLong()
    }

    override fun createViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {
        val binding = ItemBookDetailActionBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }
}