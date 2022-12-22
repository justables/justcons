package de.ilijaz.myapp.myapp.icon.renderer

class IconRendererUtil {
    companion object {
        fun indentBy(increments: Int, input: String) = input.lines().joinToString("\n") { "  ".repeat(increments) + it }
        fun indentBy(amount: Int, lines: List<String>): List<String> = lines.map { "  ".repeat(amount) + it }
    }
}