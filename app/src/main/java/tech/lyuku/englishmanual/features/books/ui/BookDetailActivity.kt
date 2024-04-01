package tech.lyuku.englishmanual.features.books.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import tech.lyuku.englishmanual.R
import tech.lyuku.englishmanual.databinding.ActivityBookDetailBinding
import tech.lyuku.englishmanual.features.books.ui.adapter.BookDetailActionAdapter
import tech.lyuku.englishmanual.models.BookItem

@AndroidEntryPoint
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
        @Suppress("DEPRECATION")
        val bookItem = intent.getParcelableExtra<BookItem>(EXTRA_KEY_BOOK_ITEM)
        if (bookItem == null) {
            finish()
            return
        }
        viewModel.init(bookItem)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val adapter = BookDetailActionAdapter { action ->
            Toast.makeText(this, "Action: ${getString(action.actionName)}", Toast.LENGTH_SHORT)
                .show()
        }.apply {
            setHasStableIds(true)
            stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        }
        adapter.submitList(viewModel.actions)
        binding.rvBookDetailActions.adapter = adapter

        viewModel.showMessageEvent.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
        viewModel.isMyBook.observe(this) {
            binding.tvMyBooks.isSelected = it
            binding.tvMyBooks.setText(
                if (it) R.string.remove_from_my_books
                else R.string.add_to_my_books
            )
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

}
