package tech.lyuku.englishmanual.features.books.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AllBooksViewModel @Inject constructor() : ViewModel() {

    private var intCounter = 0
    private val _counter = MutableLiveData("0")
    val counter: LiveData<String> = _counter

    fun increment() {
        _counter.value = intCounter++.toString()
        Log.d("TAG", "increment: $intCounter")
    }
}