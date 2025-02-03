package com.example.exam_screens.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.exam_screens.ui.screens.DetailsViewModel

class DetailsViewModelFactory(
    private val articleId: Int,
    private val repository: ArticleRepository = ArticleRepository()
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DetailsViewModel(repository, articleId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}