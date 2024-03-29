package tech.lyuku.englishmanual.features.books.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import tech.lyuku.englishmanual.databinding.ItemBookBinding
import tech.lyuku.englishmanual.models.BookItem

class BookAdapter(
    private val onBookClick: (book: BookItem, bookImgView: View) -> Unit
) : ListAdapter<BookItem, BookAdapter.ViewHolder>(BookDiffCallback()) {

    private var inflater: LayoutInflater? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (inflater == null) {
            inflater = LayoutInflater.from(parent.context)
        }
        val binding = ItemBookBinding.inflate(inflater!!, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).idBook.hashCode().toLong()
    }

    inner class ViewHolder(private val binding: ItemBookBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                onBookClick(getItem(layoutPosition), binding.ivBook)
            }
        }

        fun bind(book: BookItem) {
            binding.book = book
        }
    }

}

class BookDiffCallback : DiffUtil.ItemCallback<BookItem>() {

    override fun areItemsTheSame(oldItem: BookItem, newItem: BookItem): Boolean {
        return oldItem.idBook == newItem.idBook
    }

    override fun areContentsTheSame(oldItem: BookItem, newItem: BookItem): Boolean {
        return oldItem == newItem
    }

}