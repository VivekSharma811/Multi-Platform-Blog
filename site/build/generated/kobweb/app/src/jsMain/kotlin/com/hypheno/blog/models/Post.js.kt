package com.hypheno.blog.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
actual data class Post(
    @SerialName("_id")
    actual val id: String = "",
    actual val author: String,
    actual val date: Long,
    actual val title: String,
    actual val subtitle: String,
    actual val thumbnail: String,
    actual val content: String,
    actual val category: Category,
    actual val isPopular: Boolean,
    actual val isMain: Boolean,
    actual val isSponsored: Boolean
)

@Serializable
actual data class PostWithoutDetails(
    @SerialName("_id")
    actual val id: String = "",
    actual val author: String,
    actual val date: Long,
    actual val title: String,
    actual val subtitle: String,
    actual val thumbnail: String,
    actual val category: Category,
)