package com.example.exam_screens.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.example.exam_screens.data.ArticleRepository
import com.example.exam_screens.model.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavoritesViewModel(
    private val repository: ArticleRepository = ArticleRepository()
) : ViewModel() {
    private val _favoriteArticles = MutableStateFlow<List<Article>>(emptyList())
    val favoriteArticles: StateFlow<List<Article>> = _favoriteArticles.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun loadFavorites() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                // get the list of favorite ids from the database
                val favoriteIds = repository.getAllFavorites().asFlow().firstOrNull()?.map { it.id } ?: emptyList()
                println("Favorite IDs from database: $favoriteIds")

                if (favoriteIds.isEmpty()) {
                    println("No favorite IDs found in the database.")
                    return@launch
                }

                // fetch the full article details from the API
                val articles = favoriteIds.mapNotNull { id ->
                    try {
                        println("Favorite IDs from database: $favoriteIds")
                        repository.getArticleDetails(id)
                    } catch (e: Exception) {
                        println("Failed to fetch article with ID: $id - ${e.message}")
                        null // skip articles that fail to load
                    }
                }
                // update the state with the fetched articles
                _favoriteArticles.value = articles
            } catch (e: Exception) {
                println("Error in loadFavorites: ${e.message}")
                e.printStackTrace()
                _error.value = e.message ?: "An unknown error occurred"
            } finally {
                _isLoading.value = false
            }
        }
    }
}