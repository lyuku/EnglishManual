package tech.lyuku.englishmanual.features.books.ui

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import tech.lyuku.englishmanual.core.base.AEMBasePageStateActivity
import tech.lyuku.englishmanual.databinding.ActivityAllBooksBinding
import tech.lyuku.englishmanual.features.books.ui.adapter.BookCategoryAdapter
import tech.lyuku.englishmanual.models.BookItem

@AndroidEntryPoint
class AllBooksActivity : AEMBasePageStateActivity() {

    private val viewModel: AllBooksViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityAllBooksBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init(binding)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        viewModel.loadBooks()
    }

    private fun init(binding: ActivityAllBooksBinding) {
        val adapter = BookCategoryAdapter { book, bookImg ->
            goToBookDetailActivity(book, bookImg)
        }.apply {
            setHasStableIds(true)
            stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        }
        viewModel.allBooksCategoryList.observe(this) {
            adapter.submitList(it.subCategoryList)
            binding.rvBookCategory.visibility = View.VISIBLE
        }
        super.initPageState(viewModel.pageState, binding.flInfoViewContainer) {
            viewModel.loadBooks()
        }
        binding.rvBookCategory.adapter = adapter
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
}
