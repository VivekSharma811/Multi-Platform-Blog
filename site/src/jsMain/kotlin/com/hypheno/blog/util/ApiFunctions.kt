package com.hypheno.blog.util

import com.hypheno.blog.models.User
import com.hypheno.blog.models.UserSecured
import com.varabyte.kobweb.browser.api
import kotlinx.browser.window
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString

suspend fun checkUserExistence(user: User): UserSecured? {
    return try {
        window.api.tryPost(
            apiPath = "checkuser",
            body = Json.encodeToString(user).encodeToByteArray()
        )?.decodeToString().parseData()
    } catch (e: Exception) {
        println("CURRENT_USER")
        println(e.message)
        null
    }
}

suspend fun checkUserId(id: String): Boolean {
    return try {
        window.api.tryPost(
            apiPath = "checkuserid",
            body = Json.encodeToString(id).encodeToByteArray()
        )?.decodeToString().parseData()
    } catch (e: Exception) {
        println(e.message.toString())
        false
    }
}

inline fun <reified T> String?.parseData(): T {
    return Json.decodeFromString(this.toString())
}
