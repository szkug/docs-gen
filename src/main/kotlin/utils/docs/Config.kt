package utils.docs

import kotlinx.serialization.Serializable

/**
 * root: Relative path
 * static: static
 * flavour: GitHub | JetBrains
 * docs:
 *   - A.md
 *   - B.md
 */
@Serializable
data class Config(
    val root: String = "",
    val static: String = "static",
    val flavour: String = "GitHub",
    val title: String = "Docs",
    val docs: List<String> = emptyList()
)