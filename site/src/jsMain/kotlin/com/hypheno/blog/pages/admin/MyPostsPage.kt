package com.hypheno.blog.pages.admin

import androidx.compose.runtime.Composable
import com.hypheno.blog.components.SidePanel
import com.hypheno.blog.util.Constants.PAGE_WIDTH
import com.hypheno.blog.util.IsUserLoggedIn
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.maxWidth
import com.varabyte.kobweb.core.Page
import org.jetbrains.compose.web.css.px

@Page("myposts")
@Composable
fun MyPostsPage() {
    IsUserLoggedIn {
        MyPostsScreen()
    }
}

@Composable
fun MyPostsScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .maxWidth(PAGE_WIDTH.px)
        ) {
            SidePanel()
        }
    }
}
