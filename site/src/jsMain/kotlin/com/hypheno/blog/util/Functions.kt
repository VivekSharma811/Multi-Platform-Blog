package com.hypheno.blog.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.core.rememberPageContext
import kotlinx.browser.localStorage
import org.w3c.dom.get

@Composable
fun IsUserLoggedIn(content: @Composable () -> Unit) {
    val context = rememberPageContext()
    val remembered = remember { localStorage["remember"].toBoolean() }
    val userId = remember { localStorage["userId"] }
    var userIdExists by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        userIdExists = if(userId.isNullOrEmpty().not()) {
            checkUserId(userId!!)
        } else false

        if((remembered && userIdExists).not()) {
            context.router.navigateTo("/admin/login")
        }
    }

    if(remembered && userIdExists) {
        content()
    }
}
