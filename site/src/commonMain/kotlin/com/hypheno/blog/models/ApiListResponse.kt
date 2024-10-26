package com.hypheno.blog.models

expect sealed class ApiListResponse {
    object Idle
    class Success
    class Error
}
