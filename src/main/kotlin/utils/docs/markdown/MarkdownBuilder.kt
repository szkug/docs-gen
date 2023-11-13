package utils.docs.markdown

import kotlinx.html.*
import org.intellij.markdown.ast.ASTNode
import org.intellij.markdown.flavours.MarkdownFlavourDescriptor
import org.intellij.markdown.flavours.gfm.GFMFlavourDescriptor
import org.intellij.markdown.html.HtmlGenerator
import org.intellij.markdown.parser.MarkdownParser
import org.slf4j.LoggerFactory
import utils.docs.resource.*
import java.io.File
import java.lang.StringBuilder
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date


fun HTML.buildHeader(file: File) {
    head {
        title(file.name.removePrefix(".md"))
        link(rel = LinkRel.stylesheet, href = StylePath, type = LinkType.textCss)
        link(rel = LinkRel.stylesheet, href = ScrollbarStylePath, type = LinkType.textCss)
        link(rel = LinkRel.stylesheet, href = CheckListStylePath, type = LinkType.textCss)
        link(rel = LinkRel.stylesheet, href = HlStylePath, type = LinkType.textCss)
    }
}

fun DIV.buildNav(target: File, files: List<File>, title: String) {
    nav(classes = "topic-nav") {

        h1(classes = "nav-title") { text(title) }

        for (file in files) {
            val classes = StringBuilder("nav-item")
            if (file === target) {
                classes.append(" nav-item-select")
            }
            a(classes = classes.toString(), href = file.name) {
                text(file.name.removeSuffix(".md"))
            }
        }
    }
}

val timeFormatter = SimpleDateFormat("yyyy-MM-dd HH:mm")

fun DIV.buildContent(file: File) = with(MarkdownBuildConfig) {
    val lastModifyTime = file.lastModified()
    val date = Date(lastModifyTime)
    val timeStr = timeFormatter.format(date)
    val content = file.readText()
    val tree = parser.buildMarkdownTreeFromString(content)
    val generator = HtmlGenerator(content, tree, flavour)
    val html = generator.generateHtml(visitor)

    div(classes = "markdown-content") {

        p("modify-time") { text("Last modified: $timeStr") }

        unsafe { +html }

        div(classes = "space") {  }
    }

    script(type = ScriptType.textJavaScript, src = HlJsRef) {}
}




