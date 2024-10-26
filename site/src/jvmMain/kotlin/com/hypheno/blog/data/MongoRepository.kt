package com.hypheno.blog.data

import com.hypheno.blog.models.Post
import com.hypheno.blog.models.User

interface MongoRepository {
    suspend fun checkUserExistence(user: User): User?

    suspend fun checkUserId(id: String): Boolean

    suspend fun addPost(post: Post): Boolean
}
