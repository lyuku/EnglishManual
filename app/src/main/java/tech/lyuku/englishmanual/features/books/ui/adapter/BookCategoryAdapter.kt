package tech.lyuku.englishmanual.features.books.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import tech.lyuku.englishmanual.databinding.ItemBookCategoryBinding
import tech.lyuku.englishmanual.models.BookItem
import tech.lyuku.englishmanual.models.SubCategory

class BookCategoryAdapter(
    private val onBookClicked: (book: BookItem, bookImgView: View) -> Unit
) :
    ListAdapter<SubCategory, BookCategoryAdapter.ViewHolder>(BookCategoryDiffCallback()) {

    private var inflater: LayoutInflater? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (inflater == null) {
            inflater = LayoutInflater.from(parent.context)
        }

        val binding = ItemBookCategoryBinding.inflate(inflater!!, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).idCategory.hashCode().toLong()
    }

    /**
     * Adapter
     */
    inner class ViewHolder(val binding: ItemBookCategoryBinding) : RecyclerView.ViewHolder(binding.root) {

        private val moviesAdapter by lazy {
            val adapter = BookAdapter(onBookClicked).apply {
                setHasStableIds(true)
                stateRestorationPolicy = StateRestorationPolicy.PREVENT_WHEN_EMPTY
            }
            binding.rvBooks.adapter = adapter
            adapter
        }

        fun bind(category: SubCategory) {
            binding.bookCategory = category
            moviesAdapter.submitList(category.bookList)
        }
    }
}

class BookCategoryDiffCallback : DiffUtil.ItemCallback<SubCategory>() {

    override fun areItemsTheSame(oldItem: SubCategory, newItem: SubCategory): Boolean {
        return oldItem.idCategory == newItem.idCategory
    }

    override fun areContentsTheSame(oldItem: SubCategory, newItem: SubCategory): Boolean {
        return oldItem == newItem
    }
}