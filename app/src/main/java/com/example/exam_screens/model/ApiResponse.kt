package com.example.exam_screens.model

import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<Article>
)