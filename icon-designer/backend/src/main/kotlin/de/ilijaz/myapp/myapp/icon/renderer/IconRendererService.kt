package de.ilijaz.myapp.myapp.icon.renderer

import de.ilijaz.myapp.myapp.icon.domain.Icon
import org.springframework.stereotype.Service

@Service
class IconRendererService {
    companion object {
        fun indentBy(input: String, increments: Int) = input.lines().joinToString("\n") { "  ".repeat(increments) + it }
        fun indentBy(lines: List<String>, amount: Int): List<String> = lines.map { "  ".repeat(amount) + it }
    }


    fun render(icon: Icon): String {
        return IconRenderer(icon).render()
    }


}