package utils.docs.plugins

import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.routing.*
import kotlinx.html.*
import utils.docs.markdown.buildContent
import utils.docs.markdown.buildHeader
import utils.docs.markdown.buildNav
import java.io.File

fun Application.configureMarkdownPages(root: String, docs: List<String>) {

    val files = docs.map { File(root, it) }

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