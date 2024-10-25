package com.hypheno.blog.pages.admin

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.hypheno.blog.models.Theme
import com.hypheno.blog.models.User
import com.hypheno.blog.models.UserSecured
import com.hypheno.blog.navigation.Screen
import com.hypheno.blog.styles.LoginInputStyle
import com.hypheno.blog.util.Constants.FONT_FAMILY
import com.hypheno.blog.util.Id.passwordInput
import com.hypheno.blog.util.Id.usernameInput
import com.hypheno.blog.util.Res
import com.hypheno.blog.util.checkUserExistence
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
import com.varabyte.kobweb.compose.ui.modifiers.border
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.id
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.outline
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.textAlign
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.style.toModifier
import com.varabyte.kobweb.silk.components.text.SpanText
import kotlinx.browser.document
import kotlinx.browser.localStorage
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Input
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.set

@Page
@Composable
fun LoginScreen(modifier: Modifier = Modifier) {
    val scope = rememberCoroutineScope()
    val context = rememberPageContext()
    var errorText by remember { mutableStateOf(" ") }
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .padding(leftRight = 50.px, top = 50.px, bottom = 24.px)
                .background(Theme.LightGray.rgb),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                src = Res.Image.logo,
                description = "Logo",
                modifier = Modifier
                    .margin(50.px)
                    .width(100.px)
            )
            Input(
                type = InputType.Text,
                attrs = LoginInputStyle.toModifier()
                    .id(usernameInput)
                    .margin(bottom = 12.px)
                    .width(350.px)
                    .height(54.px)
                    .padding(leftRight = 20.px)
                    .background(Colors.White)
                    .fontFamily(FONT_FAMILY)
                    .fontSize(14.px)
                    .outline(
                        width = 0.px,
                        style = LineStyle.None,
                        color = Colors.Transparent
                    )
                    .toAttrs {
                        attr("placeholder", "Username")
                    }
            )
            Input(
                type = InputType.Password,
                attrs = LoginInputStyle.toModifier()
                    .id(passwordInput)
                    .margin(bottom = 12.px)
                    .width(350.px)
                    .height(54.px)
                    .padding(leftRight = 20.px)
                    .background(Colors.White)
                    .fontFamily(FONT_FAMILY)
                    .fontSize(14.px)
                    .outline(
                        width = 0.px,
                        style = LineStyle.None,
                        color = Colors.Transparent
                    )
                    .toAttrs {
                        attr("placeholder", "Password")
                    }
            )
            Button(
                attrs = Modifier
                    .margin(bottom = 24.px)
                    .width(350.px)
                    .height(54.px)
                    .background(Theme.Primary.rgb)
                    .color(Colors.White)
                    .borderRadius(4.px)
                    .fontWeight(FontWeight.Medium)
                    .fontSize(14.px)
                    .fontFamily(FONT_FAMILY)
                    .border(
                        width = 0.px,
                        style = LineStyle.None,
                        color = Colors.Transparent
                    )
                    .outline(
                        width = 0.px,
                        style = LineStyle.None,
                        color = Colors.Transparent
                    )
                    .cursor(Cursor.Pointer)
                    .onClick {
                        scope.launch {
                            val username =
                                (document.getElementById(usernameInput) as
                                    HTMLInputElement).value
                            val password =
                                (document.getElementById(passwordInput) as
                                    HTMLInputElement).value
                            if (username.isNotEmpty() && password.isNotEmpty()) {
                                val user = checkUserExistence(
                                    User(
                                        username = username,
                                        password = password
                                    )
                                )
                                if(user != null) {
                                    rememberUserSession(true, user)
                                    context.router.navigateTo(Screen.AdminHome.route)
                                } else {
                                    errorText = "User does not exist"
                                }
                            } else {
                                errorText = "Input Fields are invalid"
                                delay(3000)
                                errorText = " "
                            }
                        }
                    }
                    .toAttrs()
            ) {
                SpanText(text = "Sign in")
            }

            SpanText(
                modifier = Modifier
                    .width(350.px)
                    .color(Colors.Red)
                    .fontFamily(FONT_FAMILY)
                    .textAlign(TextAlign.Center),
                text = errorText
            )
        }
    }
}

private fun rememberUserSession(remember: Boolean, user: UserSecured? = null) {
    localStorage["remember"] = remember.toString()
    user?.let {
        localStorage["userId"] = user._id
        localStorage["username"] = user.username
    }
}
