package utils.docs

import com.charleskorn.kaml.Yaml
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import kotlinx.serialization.decodeFromString
import org.slf4j.LoggerFactory
import utils.docs.markdown.MarkdownBuildConfig
import utils.docs.plugins.*
import java.io.File

private val logger = LoggerFactory.getLogger("ktor.application")

fun main(args: Array<String>) {
    val env = System.getProperty("user.dir")
    val filename = args.firstOrNull() ?: "config.yml"

    logger.info("Exc env = $env")
    logger.info("Config file = $filename")

    val configFile = File("$env/$filename")
    val configContent = configFile.readText()

    val config = Yaml.default.decodeFromString(Config.serializer(), configContent)

    val root = configFile.parent + "/" + config.root
    logger.info("Root dir = $root")

    logger.info("Config = $config")

    MarkdownBuildConfig.init(config.flavour)

    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = {
        module(root, config.docs, config.static, config.title)
    }).start(wait = true)
}

fun Application.module(root: String, docs: List<String>, static: String, title: String) {
    configureStaticFileRoute(root, static)
    configureMarkdownPages(root, docs, title)
}
