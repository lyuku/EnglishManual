package tech.lyuku.englishmanual.features.books.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import tech.lyuku.englishmanual.databinding.ItemBookDetailActionBinding
import tech.lyuku.englishmanual.models.BookDetailAction

class BookDetailActionAdapter(
    private val onActionClick: (action: BookDetailAction) -> Unit
) : ListAdapter<BookDetailAction, BookDetailActionAdapter.ViewHolder>(BookActionDiffCallback()) {

    private var inflater: LayoutInflater? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (inflater == null) {
            inflater = LayoutInflater.from(parent.context)
        }
        val binding = ItemBookDetailActionBinding.inflate(inflater!!, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).id.toLong()
    }

    inner class ViewHolder(private val binding: ItemBookDetailActionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                onActionClick(getItem(layoutPosition))
            }
        }

        fun bind(action: BookDetailAction) {
            binding.action = action
        }
    }

}

class BookActionDiffCallback : DiffUtil.ItemCallback<BookDetailAction>() {

    override fun areItemsTheSame(oldItem: BookDetailAction, newItem: BookDetailAction): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: BookDetailAction, newItem: BookDetailAction): Boolean {
        return oldItem == newItem
    }

}