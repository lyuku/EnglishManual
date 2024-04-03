package tech.lyuku.englishmanual.feature.books.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import tech.lyuku.englishmanual.base.core.ui.ACommonAdapter
import tech.lyuku.englishmanual.base.core.ui.ACommonViewHolder
import tech.lyuku.englishmanual.books.databinding.ItemBookBinding
import tech.lyuku.englishmanual.data.models.BookItem

class BookAdapter(
    private val onBookClick: (book: BookItem, bookImgView: View) -> Unit
) : ACommonAdapter<BookItem, BookAdapter.ViewHolder>() {

    override fun createViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {
        val binding = ItemBookBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).idBook.hashCode().toLong()
    }

    inner class ViewHolder(private val binding: ItemBookBinding) :
        ACommonViewHolder<BookItem>(binding.root) {

        init {
            binding.root.setOnClickListener {
                onBookClick(getItem(layoutPosition), binding.ivBook)
            }
        }

        override fun bind(item: BookItem) {
            binding.book = item
        }
    }

}
