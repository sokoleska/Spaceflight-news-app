package com.example.exam_screens.data

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.exam_screens.model.ApiResponse
import com.example.exam_screens.model.Article
import com.example.exam_screens.network.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ArticleRepository() {
//      API
    suspend fun getArticles(): ApiResponse {
        return withContext(Dispatchers.IO) {
            RetrofitInstance.api.getArticles()
        }
    }

    suspend fun getArticleDetails(id: Int): Article {
        return withContext(Dispatchers.IO) {
            RetrofitInstance.api.getArticleDetails(id)
        }
    }

//    DB
//  Companion object to hold the static reference to AppDatabase
    companion object {
        private lateinit var db: AppDatabase

        // Initialize the database with the application context
        fun initialize(context: Context) {
            db = AppDatabase.getDatabase(context.applicationContext)
        }

        // Provide access to the database instance
        fun getDatabase(): AppDatabase {
            if (!::db.isInitialized) {
                throw IllegalStateException("AppDatabase is not initialized. Call initialize() first.")
            }
            return db
        }
    }
    suspend fun addFavorite(articleId: Int) {
        db.favoriteDao().insert(Favorite(articleId))
    }

    suspend fun removeFavorite(articleId: Int) {
        db.favoriteDao().deleteById(articleId)
    }

    fun getAllFavorites(): LiveData<List<Favorite>> {
        return db.favoriteDao().getAllFavorites()
    }

    suspend fun isFavorite(articleId: Int): Boolean {
        return db.favoriteDao().isFavorite(articleId)
    }
}