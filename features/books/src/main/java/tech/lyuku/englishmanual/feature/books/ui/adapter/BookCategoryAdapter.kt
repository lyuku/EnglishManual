package tech.lyuku.englishmanual.feature.books.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import tech.lyuku.englishmanual.base.core.ui.ACommonAdapter
import tech.lyuku.englishmanual.base.core.ui.ACommonViewHolder
import tech.lyuku.englishmanual.books.databinding.ItemBookCategoryBinding
import tech.lyuku.englishmanual.data.models.BookItem
import tech.lyuku.englishmanual.data.models.SubCategory

class BookCategoryAdapter(
    private val onBookClicked: (book: BookItem, bookImgView: View) -> Unit
) : ACommonAdapter<SubCategory, BookCategoryAdapter.ViewHolder>() {

    override fun createViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {
        val binding = ItemBookCategoryBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).idCategory.hashCode().toLong()
    }

    inner class ViewHolder(val binding: ItemBookCategoryBinding) :
        ACommonViewHolder<SubCategory>(binding.root) {

        private val moviesAdapter by lazy {
            val adapter = BookAdapter(onBookClicked).apply {
                setHasStableIds(true)
                stateRestorationPolicy = StateRestorationPolicy.PREVENT_WHEN_EMPTY
            }
            binding.rvBooks.adapter = adapter
            adapter
        }

        override fun bind(item: SubCategory) {
            binding.bookCategory = item
            moviesAdapter.submitList(item.bookList)
        }
    }
}