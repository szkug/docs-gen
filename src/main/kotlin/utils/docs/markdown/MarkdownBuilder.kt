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


 fun HTML.buildHeader(file: File) {
    head {
        title(file.name.removePrefix(".md"))
        link(rel = LinkRel.stylesheet, href = StylePath, type = LinkType.textCss)
        link(rel = LinkRel.stylesheet, href = ScrollbarStylePath, type = LinkType.textCss)
        link(rel = LinkRel.stylesheet, href = CheckListStylePath, type = LinkType.textCss)
        link(rel = LinkRel.stylesheet, href = HlStylePath, type = LinkType.textCss)
    }
}

 fun DIV.buildNav(target: File, files: List<File>) {
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

 fun DIV.buildContent(file: File) = with(MarkdownBuildConfig) {

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




