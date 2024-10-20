package com.hypheno.blog.api

import com.hypheno.blog.data.MongoDB
import com.hypheno.blog.models.User
import com.hypheno.blog.models.UserSecured
import com.varabyte.kobweb.api.Api
import com.varabyte.kobweb.api.ApiContext
import com.varabyte.kobweb.api.data.getValue
import com.varabyte.kobweb.api.http.setBodyText
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString
import java.nio.charset.StandardCharsets
import java.security.MessageDigest

@Api(routeOverride = "checkuser")
suspend fun checkUser(context: ApiContext) {
    try {
        val userRequest = context.req.body?.decodeToString()?.let {
            Json.decodeFromString<User>(it)
        }
        val user = userRequest?.let {
            context.data.getValue<MongoDB>().checkUserExistence(
                User(username = it.username, password = it.password.hashPassword())
            )
        }
        user?.let {
            context.res.setBodyText(
                Json.encodeToString<UserSecured>(
                    UserSecured(_id = user._id, username = user.username)
                )
            )
        } ?: {
            context.res.setBodyText(
                Json.encodeToString(
                    Exception("User does not exist")
                )
            )
        }
    } catch (e: Exception) {
        context.res.setBodyText(
            Json.encodeToString(
                Exception(e.message)
            )
        )
    }
}

private fun String.hashPassword(): String {
    val messageDigest = MessageDigest.getInstance("SHA-256")
    val hashBytes =
        messageDigest.digest(this.toByteArray(StandardCharsets.UTF_8))
    val hexString = StringBuffer()

    for (byte in hashBytes) {
        hexString.append(String.format("%02x", byte))
    }
    return hexString.toString()
}
