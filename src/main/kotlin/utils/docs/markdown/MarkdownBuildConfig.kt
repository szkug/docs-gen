package utils.docs.markdown

import org.intellij.markdown.ast.ASTNode
import org.intellij.markdown.flavours.MarkdownFlavourDescriptor
import org.intellij.markdown.flavours.gfm.GFMFlavourDescriptor
import org.intellij.markdown.flavours.space.SFMFlavourDescriptor
import org.intellij.markdown.html.HtmlGenerator
import org.intellij.markdown.parser.MarkdownParser
import org.slf4j.LoggerFactory
import java.util.Deque
import java.util.LinkedList

object MarkdownBuildConfig {

    private val namePlusTags = arrayOf("h1", "h2", "h3", "h4", "h5", "h6")

    lateinit var flavour: MarkdownFlavourDescriptor

    val parser by lazy { MarkdownParser(flavour) }

    // pair a tag info combine
    // whether is title to consume this title
    data class TitleTag(val isTitle: Boolean, var consume: Boolean = false)

    val visitor = object : HtmlGenerator.TagRenderer {

        private var tagTitleStack: Deque<TitleTag> = LinkedList()

        private val logger = LoggerFactory.getLogger("markdown.html")

        private fun CharSequence.withLog(tag: String) = apply {

            logger.debug("$tag ${tagTitleStack.firstOrNull()}: $this")
        }

        override fun openTag(
            node: ASTNode,
            tagName: CharSequence,
            vararg attributes: CharSequence?,
            autoClose: Boolean
        ): CharSequence {
            var curTitleTag = false
            val str = buildString {
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
            }

            // push whether this tag is title when not close this tag
            if (!autoClose) tagTitleStack.push(TitleTag(curTitleTag))

            return str.withLog("openTag")
        }

        override fun closeTag(tagName: CharSequence): CharSequence = "</$tagName>".withLog("closeTag").also {
            tagTitleStack.pop()
        }

        /**
         * This method maybe called multiple times within a tag.
         */
        override fun printHtml(html: CharSequence): CharSequence = if (tagTitleStack.first.let { it.isTitle && !it.consume }) {
            """"$html">$html"""
        } else {
            html
        }.withLog("printHtml").also { tagTitleStack.first.consume = true }
    }

    fun init(flavour: String) {
        this.flavour = if (flavour == "JetBrains") {
            SFMFlavourDescriptor()
        } else {
            GFMFlavourDescriptor()
        }
    }

}