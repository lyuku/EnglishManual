package tech.lyuku.englishmanual.features.books.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import tech.lyuku.englishmanual.databinding.ActivityBookDetailBinding
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
        init()
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }

    private fun init() {
        val bookItem = intent.getParcelableExtra<BookItem>(EXTRA_KEY_BOOK_ITEM)
        viewModel.init(bookItem)
        viewModel.closeActivity.observe(this) {
            finish()
        }
    }

}
