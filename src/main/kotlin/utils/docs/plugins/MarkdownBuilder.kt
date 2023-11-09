package utils.docs.plugins

import kotlinx.html.*
import org.intellij.markdown.ast.ASTNode
import org.intellij.markdown.flavours.commonmark.CommonMarkFlavourDescriptor
import org.intellij.markdown.flavours.gfm.GFMFlavourDescriptor
import org.intellij.markdown.html.HtmlGenerator
import org.intellij.markdown.parser.MarkdownParser
import org.slf4j.LoggerFactory
import java.io.File
import java.lang.StringBuilder


private val flavour = GFMFlavourDescriptor()
private val parser = MarkdownParser(flavour)

private val namePlusTags = arrayOf("h1", "h2", "h3", "h4", "h5", "h6")

private val visitor = object : HtmlGenerator.TagRenderer {

    private var curTitleTag = false

    private val logger = LoggerFactory.getLogger("markdown.html")

    private fun CharSequence.withLog(tag: String) = apply { logger.info("$tag: $this") }

    override fun openTag(node: ASTNode, tagName: CharSequence, vararg attributes: CharSequence?, autoClose: Boolean): CharSequence {
        return buildString {
            curTitleTag = false
            append("<$tagName")
            for (attribute in attributes) {
                if (attribute != null) {
                    append(" $attribute")
                }
            }
            if (tagName in namePlusTags) {
                curTitleTag = true
                append(" id=")
            } else if (autoClose) {
                append(" />")
            } else {
                append(">")
            }
        }.withLog("openTag")
    }

    override fun closeTag(tagName: CharSequence): CharSequence = "</$tagName>".withLog("closeTag")

    override fun printHtml(html: CharSequence): CharSequence = if (curTitleTag) {
        """"$html">$html"""
    } else {
        html
    }.withLog("printHtml")
}

 fun HTML.buildHeader(file: File) {
    head {
        title(file.name.removePrefix(".md"))
        link(rel = LinkRel.stylesheet, href = StylePath, type = LinkType.textCss)
        link(rel = LinkRel.stylesheet, href = HlStylePath, type = LinkType.textCss)
    }
}

 fun DIV.buildNav(target: File, files: Array<File>) {
    nav(classes = "topic-nav") {
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

 fun DIV.buildContent(file: File) {

    val content = file.readText()
    val tree = parser.buildMarkdownTreeFromString(content)
    val generator = HtmlGenerator(content, tree, flavour)
    val html = generator.generateHtml(visitor)

    div(classes = "markdown-content") {
        unsafe { +html }
    }

    aside(classes = "") {

    }

    script(type = ScriptType.textJavaScript, src = HlJsRef) {}
}




