package tech.lyuku.englishmanual.features.books.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import tech.lyuku.englishmanual.databinding.ActivityBookDetailBinding
import tech.lyuku.englishmanual.features.books.ui.adapter.BookDetailActionAdapter
import tech.lyuku.englishmanual.models.BookItem

class BookDetailActivity : AppCompatActivity() {

    private val viewModel: BookDetailViewModel by viewModels()

    companion object {
        private const val EXTRA_KEY_BOOK_ITEM = "extra_key_book_item"
        fun getStartIntent(context: Context, bookItem: BookItem): Intent {
            return Intent(context, BookDetailActivity::class.java).apply {
                putExtra(EXTRA_KEY_BOOK_ITEM, bookItem)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityBookDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init(binding)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }

    private fun init(binding: ActivityBookDetailBinding) {
        val bookItem = intent.getParcelableExtra<BookItem>(EXTRA_KEY_BOOK_ITEM)
        viewModel.init(bookItem)

        val adapter = BookDetailActionAdapter { action ->
            Toast.makeText(this, "Action: ${getString(action.actionName)}", Toast.LENGTH_SHORT).show()
        }.apply {
            setHasStableIds(true)
            stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        }
        adapter.submitList(viewModel.actions)
        binding.rvBookDetailActions.adapter = adapter

        viewModel.closeActivity.observe(this) {
            finish()
        }
    }

}
