package utils.docs.markdown

import org.intellij.markdown.ast.ASTNode
import org.intellij.markdown.flavours.MarkdownFlavourDescriptor
import org.intellij.markdown.flavours.gfm.GFMFlavourDescriptor
import org.intellij.markdown.flavours.space.SFMFlavourDescriptor
import org.intellij.markdown.html.HtmlGenerator
import org.intellij.markdown.parser.MarkdownParser
import org.slf4j.LoggerFactory

object MarkdownBuildConfig {

    private val namePlusTags = arrayOf("h1", "h2", "h3", "h4", "h5", "h6")

    lateinit var flavour: MarkdownFlavourDescriptor

    val parser by lazy { MarkdownParser(flavour) }

    val visitor = object : HtmlGenerator.TagRenderer {

        private var curTitleTag = false

        private val logger = LoggerFactory.getLogger("markdown.html")

        private fun CharSequence.withLog(tag: String) = apply {
            // logger.debug("$tag: $this")
        }

        override fun openTag(
            node: ASTNode,
            tagName: CharSequence,
            vararg attributes: CharSequence?,
            autoClose: Boolean
        ): CharSequence {
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

    fun init(flavour: String) {
        this.flavour = if (flavour == "JetBrains") {
            SFMFlavourDescriptor()
        } else {
            GFMFlavourDescriptor()
        }
    }

}