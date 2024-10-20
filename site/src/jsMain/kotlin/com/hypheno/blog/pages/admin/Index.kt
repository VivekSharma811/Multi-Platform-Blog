package com.hypheno.blog.pages.admin

import androidx.compose.runtime.Composable
import com.hypheno.blog.util.IsUserLoggedIn
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.core.Page

@Page
@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    IsUserLoggedIn {

    }
}