package com.hypheno.blog.pages

import androidx.compose.runtime.*
import com.hypheno.blog.components.CategoryNavigationItems
import com.hypheno.blog.components.OverflowSidePanel
import com.hypheno.blog.models.ApiListResponse
import com.hypheno.blog.models.Constants.POSTS_PER_PAGE
import com.hypheno.blog.models.PostWithoutDetails
import com.hypheno.blog.sections.HeaderSection
import com.hypheno.blog.sections.MainSection
import com.hypheno.blog.sections.PostsSection
import com.hypheno.blog.sections.SponsoredPostsSection
import com.hypheno.blog.util.fetchLatestPosts
import com.hypheno.blog.util.fetchMainPosts
import com.hypheno.blog.util.fetchSponsoredPosts
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.dom.Text

@Page
@Composable
fun HomePage() {
    val scope = rememberCoroutineScope()
    val breakpoint = rememberBreakpoint()
    var overflowOpened by remember { mutableStateOf(false) }
    var mainPosts by remember { mutableStateOf<ApiListResponse>(ApiListResponse.Idle) }
    val latestPosts = remember { mutableStateListOf<PostWithoutDetails>() }
    var latestPostsToSkip by remember { mutableStateOf(0) }
    val sponsoredPosts = remember { mutableStateListOf<PostWithoutDetails>() }
    var showMoreLatest by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        fetchMainPosts(
            onSuccess = { mainPosts = it },
            onError = {}
        )
        fetchLatestPosts(
            skip = latestPostsToSkip,
            onSuccess = { response ->
                if (response is ApiListResponse.Success) {
                    latestPosts.addAll(response.data)
                    latestPostsToSkip += POSTS_PER_PAGE
                    if (response.data.size >= POSTS_PER_PAGE) showMoreLatest = true
                }
            },
            onError = {}
        )
        fetchSponsoredPosts(
            onSuccess = { response ->
                if (response is ApiListResponse.Success) {
                    sponsoredPosts.addAll(response.data)
                }
            },
            onError = {}
        )
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (overflowOpened) {
            OverflowSidePanel(
                onMenuClose = { overflowOpened = false },
                content = { CategoryNavigationItems(vertical = true) }
            )
        }
        HeaderSection(
            breakpoint = breakpoint,
            onMenuOpen = { overflowOpened = true }
        )
        MainSection(breakpoint = breakpoint, posts = mainPosts)
        PostsSection(
            breakpoint = breakpoint,
            posts = latestPosts,
            title = "Latest Posts",
            showMoreVisibility = showMoreLatest,
            onShowMore = {
                scope.launch {
                    fetchLatestPosts(
                        skip = latestPostsToSkip,
                        onSuccess = { response ->
                            if (response is ApiListResponse.Success) {
                                if (response.data.isNotEmpty()) {
                                    if (response.data.size < POSTS_PER_PAGE) {
                                        showMoreLatest = false
                                    }
                                    latestPosts.addAll(response.data)
                                    latestPostsToSkip += POSTS_PER_PAGE
                                } else {
                                    showMoreLatest = false
                                }
                            }
                        },
                        onError = {}
                    )
                }
            },
            onClick = {
            }
        )
        SponsoredPostsSection(
            breakpoint = breakpoint,
            posts = sponsoredPosts,
            onClick = {}
        )
    }
}
