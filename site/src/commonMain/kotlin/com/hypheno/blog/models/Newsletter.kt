package com.hypheno.blog.models

import kotlinx.serialization.Serializable

@Serializable
data class Newsletter(
    val email: String
)
