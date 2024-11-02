package com.hypheno.blog.sections

import androidx.compose.runtime.Composable
import com.hypheno.blog.components.PostsView
import com.hypheno.blog.models.PostWithoutDetails
import com.hypheno.blog.util.Constants.PAGE_WIDTH
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.maxWidth
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import org.jetbrains.compose.web.css.px

@Composable
fun PostsSection(
    breakpoint: Breakpoint,
    posts: List<PostWithoutDetails>,
    title: String,
    showMoreVisibility: Boolean,
    onShowMore: () -> Unit,
    onClick: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .margin(topBottom = 50.px)
            .maxWidth(PAGE_WIDTH.px),
        contentAlignment = Alignment.TopCenter
    ) {
        PostsView(
            breakpoint = breakpoint,
            posts = posts,
            title = title,
            showMoreVisibility = showMoreVisibility,
            onShowMore = onShowMore,
            onClick = onClick
        )
    }
}
