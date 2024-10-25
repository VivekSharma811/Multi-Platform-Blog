package com.hypheno.blog.pages.admin

import androidx.compose.runtime.Composable
import com.hypheno.blog.components.AdminPageLayout
import com.hypheno.blog.models.Theme
import com.hypheno.blog.navigation.Screen
import com.hypheno.blog.util.Constants.PAGE_WIDTH
import com.hypheno.blog.util.IsUserLoggedIn
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.background
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.maxWidth
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.position
import com.varabyte.kobweb.compose.ui.modifiers.size
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.components.icons.fa.FaPlus
import com.varabyte.kobweb.silk.components.icons.fa.IconSize
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import org.jetbrains.compose.web.css.Position
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.vh

@Page
@Composable
fun HomePage(modifier: Modifier = Modifier) {
    IsUserLoggedIn {
        HomeScreen()
    }
}

@Composable
fun HomeScreen() {
    AdminPageLayout {
        AddButton()
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
