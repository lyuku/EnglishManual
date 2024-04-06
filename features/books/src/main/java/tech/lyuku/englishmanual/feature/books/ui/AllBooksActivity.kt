package tech.lyuku.englishmanual.feature.books.ui

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import tech.lyuku.englishmanual.base.core.base.AEMBasePageStateActivity
import tech.lyuku.englishmanual.base.core.base.PageState
import tech.lyuku.englishmanual.base.core.base.PageStateObserver
import tech.lyuku.englishmanual.books.databinding.ActivityAllBooksBinding
import tech.lyuku.englishmanual.data.models.BookItem
import tech.lyuku.englishmanual.feature.books.ui.adapter.BookCategoryAdapter
import javax.inject.Inject

@AndroidEntryPoint
class AllBooksActivity : AEMBasePageStateActivity() {

    private val viewModel: AllBooksViewModel by viewModels()
    private lateinit var binding: ActivityAllBooksBinding


    @Inject
    lateinit var stateObserver: PageStateObserver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAllBooksBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        viewModel.loadBooks()
    }

    private fun init() {
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
        viewModel.pageState.observe(this) {
            stateObserver.onPageStateChange(it)
            when (it) {
                is PageState.Error -> {
                    super.showErrorView(it.message) {
                        viewModel.loadBooks()
                    }
                }

                is PageState.Loading -> {
                    super.showLoadingView()
                }

                is PageState.Loaded -> {
                    super.hideInfoViews()
                }

                is PageState.Empty -> {
                    super.showEmptyView()
                }
            }
        }
        binding.rvBookCategory.adapter = adapter
    }

    override fun getInfoContainer(): ViewGroup {
        return binding.flInfoViewContainer
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
