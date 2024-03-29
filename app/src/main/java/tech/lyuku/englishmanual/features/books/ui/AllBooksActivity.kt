package tech.lyuku.englishmanual.features.books.ui

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import tech.lyuku.englishmanual.databinding.ActivityAllBooksBinding
import tech.lyuku.englishmanual.features.books.ui.adapter.BookCategoryAdapter
import tech.lyuku.englishmanual.models.BookItem

@AndroidEntryPoint
class AllBooksActivity : AppCompatActivity() {

    private val viewModel: AllBooksViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityAllBooksBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = BookCategoryAdapter { book, bookImg ->
            goToBookDetailActivity(book, bookImg)
        }.apply {
            setHasStableIds(true)
            stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        }
        viewModel.allBooksCategoryList.observe(this) {
            adapter.submitList(it.subCategoryList)
        }

        binding.rvBookCategory.adapter = adapter

        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }

    private fun goToBookDetailActivity(book: BookItem, bookImg: View) {
        val transition = ActivityOptionsCompat.makeSceneTransitionAnimation(
            this,
            bookImg,
            "bookImage"
        )
        val intent = BookDetailActivity.getStartIntent(this, book)
        startActivity(intent, transition.toBundle())
    }

    override fun onStart() {
        super.onStart()
        viewModel.loadBooks()
    }

}
