package tech.lyuku.englishmanual.features.books.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import tech.lyuku.englishmanual.databinding.ActivityAllBooksBinding

@AndroidEntryPoint
class AllBooksActivity : AppCompatActivity() {

    private val viewModel: AllBooksViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityAllBooksBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }


}