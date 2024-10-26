package com.hypheno.blog.models

expect class Post {
    val id: String
    val author: String
    val date: Long
    val title: String
    val subtitle: String
    val thumbnail: String
    val content: String
    val category: Category
    val isPopular: Boolean
    val isMain: Boolean
    val isSponsored: Boolean
}
