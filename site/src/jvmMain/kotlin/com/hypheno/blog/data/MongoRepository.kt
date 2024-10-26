package com.hypheno.blog.data

import com.hypheno.blog.models.Post
import com.hypheno.blog.models.PostWithoutDetails
import com.hypheno.blog.models.User

interface MongoRepository {
    suspend fun checkUserExistence(user: User): User?

    suspend fun checkUserId(id: String): Boolean

    suspend fun addPost(post: Post): Boolean

    suspend fun readMyPosts(skip: Int, author: String): List<PostWithoutDetails>

    suspend fun deleteSelectedPosts(ids: List<String>): Boolean
}
