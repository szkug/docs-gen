package utils.docs.plugins

import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.routing.*
import kotlinx.html.*
import org.intellij.markdown.ast.ASTNode
import org.intellij.markdown.flavours.commonmark.CommonMarkFlavourDescriptor
import org.intellij.markdown.html.HtmlGenerator
import org.intellij.markdown.parser.MarkdownParser
import java.io.File
import java.nio.file.Files

fun Application.configureMarkdownPages(root: File) {

    val files = root.listFiles { file: File ->
        file.name.endsWith(".md")
    }!!

    routing {

        get("/") {
            call.respondHtml {
                body {
                    for (file in files) {
                        div {
                            val name = file.name
                            a(href = name) { text(name) }
                        }
                    }
                }
            }
        }

        for (file in files) {
            get(file.name) {
                call.respondHtml {
                    buildHeader(file)

                    body {
                        div(classes = "flex-layout") {
                            div(classes = "flex-nav") { buildNav(file, files) }
                            div(classes = "flex-content") { buildContent(file) }
                        }
                    }
                }
            }
        }
    }
}