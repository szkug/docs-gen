package utils.docs

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.slf4j.LoggerFactory
import utils.docs.plugins.*
import java.io.File

private val logger = LoggerFactory.getLogger("ktor.application")

fun main(args: Array<String>) {
    // 需要两个参数
    val path = args[0] // src/test/resources
    val static = args[1] // images

    val env = System.getProperty("user.dir")
    val root = File("$env/$path")

    logger.info("Root Dir = $root")
    logger.info("Static Dir = $static")

    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = {
      module(root, static)
    }).start(wait = true)
}

fun Application.module(root: File, static: String) {
    configureStaticFileRoute(root, static)
    configureMarkdownPages(root)
}
