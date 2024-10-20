package com.hypheno.blog.models

expect class User {
    val _id: String
    val username: String
    val password: String
}

expect class UserSecured {
    val _id: String
    val username: String
}
