package com.example.exam_screens.model

import kotlinx.serialization.Serializable

@Serializable
data class Article(
    val id: Int,
    val title: String,
    val authors: List<Author>,
    val url: String,
    val image_url: String,
    val news_site: String,
    val summary: String,
    val published_at: String,
    val updated_at: String,
    val featured: Boolean,
    val launches: List<Launch>,
    val events: List<Event>
)

@Serializable
data class Author(
    val name: String,
    val socials: Map<String, String>?
)

@Serializable
data class Launch(
    val id: String? = null,
    val provider: String? = null
)

@Serializable
data class Event(
    val id: String? = null,
    val provider: String? = null
)