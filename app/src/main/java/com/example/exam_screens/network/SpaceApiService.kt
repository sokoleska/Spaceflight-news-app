package com.example.exam_screens.network

import com.example.exam_screens.model.ApiResponse
import com.example.exam_screens.model.Article
import retrofit2.http.GET
import retrofit2.http.Path

interface SpaceApiService {
    @GET("articles")
    suspend fun getArticles(): ApiResponse

    @GET("articles/{id}")
    suspend fun getArticleDetails(@Path("id") id: Int): Article
}