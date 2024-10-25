package com.hypheno.blog.pages.admin

import androidx.compose.runtime.Composable
import com.hypheno.blog.components.AdminPageLayout
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

@Page(routeOverride = "create")
@Composable
fun CreatePostPage() {
    IsUserLoggedIn {
        CreatePostScreen()
    }
}

@Composable
fun CreatePostScreen() {
    AdminPageLayout {

    }
}