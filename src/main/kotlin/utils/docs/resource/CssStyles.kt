package utils.docs.resource

import kotlinx.css.Color
import kotlinx.css.Margin
import kotlinx.css.Padding
import kotlinx.css.px

const val StylePath = "/styles.css"
const val ScrollbarStylePath = "/scrollbar.css"
const val CheckListStylePath = "/checklist.css"
const val HlStylePath = "/prism.css"

object CssStyles {

    val background = Color("#19191c")
    val defaultTextColor = Color("#ffffffcc")
    val strongTextColor = Color.white
    val borderColor = Color("#ffffff33")
    val linkColor = Color("#4c8ef9")
    val linkHoverColor = Color("#80b0ff")
    val blockBackground = Color("#ffffff0d")
    val tableBorderColor = Color("#474749")
    val tableHeaderColor = Color("#252528")
    val tablePadding = 16.px

    val navBackground = Color("#19191c")
    val navHoverBackground = Color("#ffffff0d")
    val navSelectBackground = Color("#307fff")

    val navTop = 30.px
    val navItemWidth = 300.px
    val navLineHeight = 20.px

    val postMaxWidth = 900.px
    val postPadding = 32.px

    val titleTop = 48.px
    val lineTop = 20.px
    val listTop = 10.px
    val blockquoteTop = 32.px

    val fontName = "JetBrainsMono"

    val fontFamily =
        "$fontName,Inter,system-ui,-apple-system,BlinkMacSystemFont,Segoe UI,Roboto,Oxygen,Ubuntu,Cantarell,Droid Sans,Helvetica Neue,Arial,sans-serif;"

    val textSize = 16.px
    val codeTextSize = 15.px
    val navTextSize = 13.px
    val lineHeightRadio = 1.8

    val navPadding = Padding(vertical = 10.px, horizontal = 20.px)
    val blockPadding = Padding(vertical = 12.px, horizontal = 16.px)
    val blockRadius = 8.px
    val codeLinePadding = Padding(vertical = 4.px, horizontal = 6.px)
    val codeLineMargin = Margin(vertical = 0.px, horizontal = 2.px)
    val codeLineRadius = 4.px
}