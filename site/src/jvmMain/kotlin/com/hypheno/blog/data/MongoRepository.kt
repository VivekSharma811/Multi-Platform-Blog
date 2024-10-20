package com.hypheno.blog.data

import com.hypheno.blog.models.User

interface MongoRepository {
    suspend fun checkUserExistence(user: User): User?
}
