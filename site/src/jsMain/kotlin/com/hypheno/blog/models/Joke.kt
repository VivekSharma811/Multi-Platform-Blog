package com.hypheno.blog.models

import kotlinx.serialization.Serializable

@Serializable
data class Joke(
    val id: Int,
    val joke: String
)