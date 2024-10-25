package com.hypheno.blog.components

import androidx.compose.runtime.Composable
import com.hypheno.blog.models.Theme
import com.hypheno.blog.navigation.Screen
import com.hypheno.blog.styles.NavigationItemStyle
import com.hypheno.blog.util.Constants.COLLAPSED_PANEL_HEIGHT
import com.hypheno.blog.util.Constants.FONT_FAMILY
import com.hypheno.blog.util.Constants.SIDE_PANEL_WIDTH
import com.hypheno.blog.util.Id.navigationText
import com.hypheno.blog.util.Id.svgParent
import com.hypheno.blog.util.Id.vectorIcon
import com.hypheno.blog.util.Res
import com.hypheno.blog.util.logout
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.dom.svg.Path
import com.varabyte.kobweb.compose.dom.svg.Svg
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxHeight
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.id
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.position
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.compose.ui.modifiers.zIndex
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.compose.ui.thenIf
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.icons.fa.FaBars
import com.varabyte.kobweb.silk.components.icons.fa.FaXmark
import com.varabyte.kobweb.silk.components.icons.fa.IconSize
import com.varabyte.kobweb.silk.components.style.toModifier
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import org.jetbrains.compose.web.css.Position
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.vh

@Composable
fun SidePanel(
    onMenuClick: () -> Unit
) {
    val breakpoint = rememberBreakpoint()
    if (breakpoint > Breakpoint.MD) {
        VerticalPanel()
    } else {
        CollapsedSidePanel(onMenuClick = onMenuClick)
    }
}

@Composable
private fun VerticalPanel(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .padding(leftRight = 40.px, topBottom = 50.px)
            .width(SIDE_PANEL_WIDTH.px)
            .height(100.vh)
            .position(Position.Fixed)
            .backgroundColor(Theme.Secondary.rgb)
            .zIndex(9)
    ) {
        Image(
            src = Res.Image.logo,
            description = "Logo",
            modifier = Modifier
                .margin(bottom = 60.px)
        )
        SpanText(
            text = "Dashboard",
            modifier = Modifier
                .fontFamily(FONT_FAMILY)
                .fontSize(14.px)
                .color(Theme.HalfWhite.rgb)
                .margin(bottom = 30.px)
        )
        NavigationItems()
    }
}

@Composable
private fun CollapsedSidePanel(
    modifier: Modifier = Modifier,
    onMenuClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(COLLAPSED_PANEL_HEIGHT.px)
            .padding(leftRight = 25.px)
            .backgroundColor(Theme.Secondary.rgb),
        verticalAlignment = Alignment.CenterVertically
    ) {
        FaBars(
            size = IconSize.XL,
            modifier = Modifier
                .margin(right = 24.px)
                .color(Colors.White)
                .cursor(Cursor.Pointer)
                .onClick { onMenuClick() }
        )
        Image(
            src = Res.Image.logo,
            description = "Logo",
            modifier = Modifier
                .width(80.px)
        )
    }
}

@Composable
fun OverflowSidePanel(
    modifier: Modifier = Modifier,
    onMenuClose: () -> Unit
) {
    val breakpoint = rememberBreakpoint()
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(100.vh)
            .position(Position.Fixed)
            .zIndex(9)
            .backgroundColor(Theme.HalfBlack.rgb)
    ) {
        Column(
            modifier = Modifier
                .padding(all = 24.px)
                .fillMaxHeight()
                .width(if (breakpoint < Breakpoint.MD) 50.percent else 25.percent)
                .backgroundColor(Theme.Secondary.rgb)
        ) {
            Row(
                modifier = Modifier
                    .margin(bottom = 60.px),
                verticalAlignment = Alignment.CenterVertically
            ) {
                FaXmark(
                    size = IconSize.LG,
                    modifier = Modifier
                        .margin(right = 20.px)
                        .color(Colors.White)
                        .cursor(Cursor.Pointer)
                        .onClick { onMenuClose() }
                )
                Image(
                    src = Res.Image.logo,
                    description = "Logo",
                    modifier = Modifier.width(80.px)
                )
            }
            NavigationItems()
        }
    }
}

@Composable
private fun NavigationItems() {
    val context = rememberPageContext()
    NavigationItem(
        modifier = Modifier.margin(bottom = 24.px),
        selected = context.route.path == Screen.AdminHome.route,
        title = "Home",
        icon = Res.PathIcon.home,
        onClick = {
            context.router.navigateTo(Screen.AdminHome.route)
        }
    )

    NavigationItem(
        modifier = Modifier.margin(bottom = 24.px),
        title = "Create Post",
        selected = context.route.path == Screen.AdminCreate.route,
        icon = Res.PathIcon.create,
        onClick = {
            context.router.navigateTo(Screen.AdminCreate.route)
        }
    )

    NavigationItem(
        modifier = Modifier.margin(bottom = 24.px),
        title = "My Posts",
        selected = context.route.path == Screen.AdminMyPosts.route,
        icon = Res.PathIcon.posts,
        onClick = {
            context.router.navigateTo(Screen.AdminMyPosts.route)
        }
    )

    NavigationItem(
        title = "Logout",
        icon = Res.PathIcon.logout,
        onClick = {
            logout()
            context.router.navigateTo(Screen.AdminLogin.route)
        }
    )
}

@Composable
private fun NavigationItem(
    modifier: Modifier = Modifier,
    selected: Boolean = false,
    title: String,
    icon: String,
    onClick: () -> Unit
) {
    Row(
        modifier = NavigationItemStyle.toModifier()
            .then(modifier)
            .cursor(Cursor.Pointer)
            .onClick { onClick() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.px)
    ) {
        VectorIcon(
            pathData = icon,
            selected = selected
        )
        SpanText(
            text = title,
            modifier = Modifier
                .id(navigationText)
                .fontFamily(FONT_FAMILY)
                .fontSize(16.px)
                .thenIf(
                    condition = selected,
                    other = Modifier.color(Theme.Primary.rgb)
                )
        )
    }
}

@Composable
fun VectorIcon(
    modifier: Modifier = Modifier,
    pathData: String,
    selected: Boolean
) {
    Svg(
        attrs = modifier
            .id(svgParent)
            .width(24.px)
            .height(24.px)
            .toAttrs {
                attr("viewBox", "0 0 24 24")
                attr("fill", "none")
            }
    ) {
        Path(
            attrs = Modifier
                .id(vectorIcon)
                .thenIf(
                    condition = selected,
                    other = Modifier.styleModifier {
                        property("stroke", Theme.Primary.hex)
                    }
                )
                .toAttrs {
                    attr("d", pathData)
                    attr("stroke-width", "2")
                    attr("stroke-linecap", "round")
                    attr("stroke-linejoin", "round")
                }
        )
    }
}
