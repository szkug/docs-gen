package utils.docs.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.css.*
import kotlinx.css.properties.TextDecoration
import kotlinx.css.properties.lh
import utils.docs.resource.CssStyles
import utils.docs.resource.StylePath
import java.io.File

suspend inline fun ApplicationCall.respondCss(builder: CssBuilder.() -> Unit) {
    this.respondText(CssBuilder().apply(builder).toString(), ContentType.Text.CSS)
}

fun Application.configureStaticFileRoute(root: String, dir: String) {
    routing {

        staticResources("", "")
        staticFiles(dir, File(root, dir))

        get(StylePath) {
            call.respondCss {
                stylesCss()
            }
        }
    }
}

private fun CssBuilder.stylesCss() {
    fontFace {
        fontFamily = CssStyles.fontName
        src = """url("JetBrainsMono[wght].ttf") format("ttf")"""
    }
    rule("*") {
        fontFamily = CssStyles.fontFamily
    }
    html {
        height = 100.pct
        width = 100.pct
    }
    body {
        backgroundColor = CssStyles.background
        margin = Margin(0.px)
        color = CssStyles.defaultTextColor
        height = 100.pct
        width = 100.pct
    }

    a {
        cursor = Cursor.pointer
        color = CssStyles.linkColor
        textDecoration = TextDecoration.none

        hover {
            borderBottomColor = Color.unset
            color = CssStyles.linkHoverColor
        }
    }
    p {
        marginTop = CssStyles.lineTop
        marginBottom = 0.px
        fontSize = CssStyles.textSize
        lineHeight = (CssStyles.textSize * CssStyles.lineHeightRadio).lh
    }
    li {
        marginTop = CssStyles.listTop
        fontSize = CssStyles.textSize
        lineHeight = (CssStyles.textSize * CssStyles.lineHeightRadio).lh
    }
    rule("h1, h2, h3, h4, h5, h6") {
        marginTop = CssStyles.titleTop
        color = CssStyles.strongTextColor
    }
    rule(".space") {
        height = CssStyles.spaceHeight
    }
    rule(".modify-time") {
        fontSize = CssStyles.timeTextSize
        color = CssStyles.timeTextColor
        position = Position.relative
        bottom = (-20).px
    }

    code {
        backgroundColor = CssStyles.blockBackground
        padding = CssStyles.codeLinePadding
        margin = CssStyles.codeLineMargin
        fontSize = CssStyles.codeTextSize
        lineHeight = (CssStyles.codeTextSize * CssStyles.lineHeightRadio).lh
        borderRadius = CssStyles.codeLineRadius
    }
    pre {
        marginTop = CssStyles.lineTop
        background = CssStyles.blockBackground.value.plus(" !important")
        padding = CssStyles.blockPadding
        borderRadius = CssStyles.blockRadius

        children(code.tagName) {
            padding = Padding(0.px)
            margin = Margin(0.px)
            backgroundColor = Color.transparent
        }
    }

    rule(".code-toolbar .toolbar .toolbar-item") {
        marginRight = 6.px
    }

    rule(".markdown-content") {
        maxWidth = CssStyles.postMaxWidth
        padding = Padding(CssStyles.postPadding)
        margin = Margin(LinearDimension.auto)
        height = 100.pct
    }

    blockquote {
        backgroundColor = CssStyles.blockBackground
        margin = Margin(top = CssStyles.blockquoteTop, 0.px, 0.px, 0.px)
        padding = CssStyles.blockPadding
        borderRadius = CssStyles.blockRadius
        display = Display.flex

        before {
            display = Display.block
            color = Color.white
            put("content", "url(blockquote.svg)")
            position = Position.relative
            top = 3.px
        }

        children(p.tagName) {
            margin = Margin(0.px, 0.px, 0.px, left = 12.px)
            display = Display.block
        }
    }

    rule(".flex-layout") {
        display = Display.flex
        height = 100.pct
        width = 100.pct
    }

    rule(".flex-nav") {
        height = 100.pct
        minWidth = 300.px
        borderRight = Border(width = 1.px, style = BorderStyle.solid, color = CssStyles.borderColor)
        overflow = Overflow.auto
        backgroundColor = CssStyles.navBackground
    }

    rule(".topic-nav") {
        // paddingTop = CssStyles.navTop
    }

    rule(".nav-title") {
        textAlign = TextAlign.center
        marginBottom = CssStyles.navTop
    }

    rule(".nav-item") {
        display = Display.block
        width = CssStyles.navItemWidth
        padding = CssStyles.navPadding
        cursor = Cursor.pointer
        fontSize = CssStyles.navTextSize
        color = CssStyles.defaultTextColor
        borderBottom = Border.none
        lineHeight = CssStyles.navLineHeight.lh

        hover {
            backgroundColor = CssStyles.navHoverBackground
            color = CssStyles.defaultTextColor
        }

        after {
            marginLeft = 0.px
            content = QuotedString("")
        }
    }

    rule(".nav-item-select") {
        backgroundColor = CssStyles.navSelectBackground
        color = CssStyles.strongTextColor

        hover {
            backgroundColor = CssStyles.navSelectBackground
            color = CssStyles.strongTextColor
        }
    }

    rule(".flex-content") {
        overflow = Overflow.auto
        flexGrow = 1
    }

    img {
        maxWidth = 100.pct
        marginTop = CssStyles.lineTop
    }

    table {
        borderCollapse = BorderCollapse.collapse
    }

    td {
        border = Border(1.px, BorderStyle.solid, CssStyles.tableBorderColor)
        padding = Padding(CssStyles.tablePadding)
        fontSize = CssStyles.textSize
        lineHeight = (CssStyles.textSize * CssStyles.lineHeightRadio).lh
    }

    th {
        border = Border(1.px, BorderStyle.solid, CssStyles.tableBorderColor)
        padding = Padding(CssStyles.tablePadding)
        backgroundColor = CssStyles.tableHeaderColor
        fontSize = CssStyles.textSize
        lineHeight = (CssStyles.textSize * CssStyles.lineHeightRadio).lh
        fontWeight = FontWeight.normal
    }
}

