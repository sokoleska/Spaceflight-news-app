package com.example.exam_screens.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exam_screens.data.ArticleRepository
import com.example.exam_screens.model.Article
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val repository: ArticleRepository = ArticleRepository(),
    private val articleId: Int
): ViewModel() {

    private val _article = MutableStateFlow<Article?>(null)
    val article: StateFlow<Article?> get() = _article

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _isFavorite = MutableStateFlow(false)
    val isFavorite: StateFlow<Boolean> get() = _isFavorite

    init {
        viewModelScope.launch {
            _isFavorite.value = repository.isFavorite(articleId)
        }
    }

    fun toggleFavorite() {
        viewModelScope.launch {
            if (_isFavorite.value) {
                repository.removeFavorite(articleId)
            } else {
                repository.addFavorite(articleId)
            }
            _isFavorite.value = !_isFavorite.value
        }
    }

    fun fetchArticleDetails(id: Int) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val fetchedArticle = repository.getArticleDetails(id)
                _article.value = fetchedArticle
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }
}