package com.hypheno.blog.pages.admin

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.hypheno.blog.components.AdminPageLayout
import com.hypheno.blog.models.RandomJoke
import com.hypheno.blog.models.Theme
import com.hypheno.blog.navigation.Screen
import com.hypheno.blog.util.Constants.FONT_FAMILY
import com.hypheno.blog.util.Constants.HUMOR_API_URL
import com.hypheno.blog.util.Constants.PAGE_WIDTH
import com.hypheno.blog.util.Constants.SIDE_PANEL_WIDTH
import com.hypheno.blog.util.IsUserLoggedIn
import com.hypheno.blog.util.Res
import com.varabyte.kobweb.browser.api
import com.varabyte.kobweb.browser.http.http
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.background
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.maxWidth
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.position
import com.varabyte.kobweb.compose.ui.modifiers.size
import com.varabyte.kobweb.compose.ui.modifiers.textAlign
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.icons.fa.FaPlus
import com.varabyte.kobweb.silk.components.icons.fa.IconSize
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import kotlinx.browser.localStorage
import kotlinx.browser.window
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import org.jetbrains.compose.web.css.Position
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.vh
import org.w3c.dom.get
import org.w3c.dom.set
import kotlin.js.Date

@Page
@Composable
fun HomePage(modifier: Modifier = Modifier) {
    IsUserLoggedIn {
        HomeScreen()
    }
}

@Composable
fun HomeScreen() {
    val scope = rememberCoroutineScope()
    var randomJoke: RandomJoke? by remember { mutableStateOf(null) }

    LaunchedEffect(Unit) {
        val date = localStorage["date"]
        if(date != null) {
            val difference = (Date.now() - date.toDouble())
            val hasDayPassed = difference >= 86400000
            if(hasDayPassed) {
                scope.launch {
                    try {
                        val result = window.http.get(HUMOR_API_URL).decodeToString()
                        randomJoke = Json.decodeFromString(result)
                        localStorage["date"] = Date.now().toString()
                        localStorage["joke"] = result
                    } catch (e: Exception) {
                        println(e.message)
                        randomJoke = RandomJoke(id = -1, "Unexpected error")
                    }
                }
            } else {
                try {
                    randomJoke =
                        localStorage["joke"]?.let { Json.decodeFromString(it) }
                } catch (e: Exception) {
                    println(e.message)
                    randomJoke = RandomJoke(id = -1, "Unexpected error")
                }
            }
        } else {
            scope.launch {
                try {
                    val result = window.http.get(HUMOR_API_URL).decodeToString()
                    randomJoke = Json.decodeFromString(result)
                    localStorage["date"] = Date.now().toString()
                    localStorage["joke"] = result
                } catch (e: Exception) {
                    println(e.message)
                    randomJoke = RandomJoke(id = -1, "Unexpected error")
                }
            }
        }
    }

    AdminPageLayout {
        HomeContent(randomJoke = randomJoke)
        AddButton()
    }
}

@Composable
fun HomeContent(
    modifier: Modifier = Modifier,
    randomJoke: RandomJoke?
) {
    val breakpoint = rememberBreakpoint()

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(left = if (breakpoint > Breakpoint.MD) SIDE_PANEL_WIDTH.px else 0.px),
        contentAlignment = Alignment.Center
    ) {
        randomJoke?.let {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(topBottom = 50.px),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if(randomJoke.id != -1) {
                    Image(
                        src = Res.Image.laugh,
                        description = "Laugh",
                        modifier = Modifier
                            .size(150.px)
                            .margin(bottom = 50.px)
                    )
                }
                if(randomJoke.joke.contains("Q:")) {
                    SpanText(
                        modifier = Modifier
                            .margin(bottom = 14.px)
                            .fillMaxWidth(40.percent)
                            .textAlign(TextAlign.Center)
                            .color(Theme.Secondary.rgb)
                            .fontSize(28.px)
                            .fontFamily(FONT_FAMILY)
                            .fontWeight(FontWeight.Bold),
                        text = randomJoke.joke.split(":")[1].dropLast(1)
                    )
                    SpanText(
                        modifier = Modifier
                            .fillMaxWidth(40.percent)
                            .textAlign(TextAlign.Center)
                            .color(Theme.HalfBlack.rgb)
                            .fontSize(20.px)
                            .fontFamily(FONT_FAMILY)
                            .fontWeight(FontWeight.Normal),
                        text = randomJoke.joke.split(":").last()
                    )
                } else {
                    SpanText(
                        modifier = Modifier
                            .margin(bottom = 14.px)
                            .fillMaxWidth(40.percent)
                            .textAlign(TextAlign.Center)
                            .color(Theme.Secondary.rgb)
                            .fontFamily(FONT_FAMILY)
                            .fontSize(28.px)
                            .fontWeight(FontWeight.Bold),
                        text = randomJoke.joke
                    )
                }
            }
        }
    }
}

@Composable
fun AddButton(
    modifier: Modifier = Modifier
) {
    val breakpoint = rememberBreakpoint()
    val context = rememberPageContext()
    Box(
        modifier = Modifier
            .height(100.vh)
            .fillMaxWidth()
            .maxWidth(PAGE_WIDTH.px)
            .position(Position.Fixed)
            .styleModifier {
                property("pointer-events", "none")
            },
        contentAlignment = Alignment.BottomEnd
    ) {
        Box(
            modifier = modifier
                .margin(
                    right = if (breakpoint > Breakpoint.MD) 40.px else 20.px,
                    bottom = if (breakpoint > Breakpoint.MD) 40.px else 20.px
                )
                .size(if (breakpoint > Breakpoint.MD) 80.px else 50.px)
                .borderRadius(r = 14.px)
                .cursor(Cursor.Pointer)
                .background(Theme.Primary.rgb)
                .onClick {
                    context.router.navigateTo(Screen.AdminCreate.route)
                }
                .styleModifier {
                    property("pointer-events", "auto")
                },
            contentAlignment = Alignment.Center
        ) {
            FaPlus(
                size = IconSize.LG,
                modifier = Modifier
                    .color(Colors.White)
            )
        }
    }
}
