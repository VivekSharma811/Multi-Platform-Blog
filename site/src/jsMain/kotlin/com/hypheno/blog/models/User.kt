package com.hypheno.blog.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
actual data class User(
    @SerialName(value = "_id")
    actual val _id: String = "",
    actual val username: String = "",
    actual val password: String = ""
)

@Serializable
actual data class UserSecured(
    @SerialName(value = "_id")
    actual val _id: String = "",
    actual val username: String = ""
)
