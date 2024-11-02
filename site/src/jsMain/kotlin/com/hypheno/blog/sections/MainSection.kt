package com.hypheno.blog.sections

import androidx.compose.runtime.Composable
import com.hypheno.blog.components.PostPreview
import com.hypheno.blog.models.ApiListResponse
import com.hypheno.blog.models.PostWithoutDetails
import com.hypheno.blog.models.Theme
import com.hypheno.blog.util.Constants.PAGE_WIDTH
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.maxWidth
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px

@Composable
fun MainSection(
    breakpoint: Breakpoint,
    posts: ApiListResponse
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .maxWidth(PAGE_WIDTH.px)
            .backgroundColor(Theme.Secondary.rgb),
        contentAlignment = Alignment.Center
    ) {
        when (posts) {
            is ApiListResponse.Idle -> {}
            is ApiListResponse.Success -> {
                MainPosts(breakpoint = breakpoint, posts = posts.data)
            }

            is ApiListResponse.Error -> {}
        }
    }
}

@Composable
fun MainPosts(
    breakpoint: Breakpoint,
    posts: List<PostWithoutDetails>
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(
                if (breakpoint > Breakpoint.MD) 80.percent
                else 90.percent
            )
            .margin(topBottom = 50.px)
    ) {
        PostPreview(
            post = posts.first(),
            darkTheme = true
        )
        Column(
            modifier = Modifier
                .fillMaxWidth(45.percent)
                .margin(left = 50.px)
        ) {
            posts.drop(1).forEach {
                PostPreview(
                    post = it,
                    darkTheme = true
                )
            }
        }
    }
}
