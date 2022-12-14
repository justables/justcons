package de.ilijaz.myapp.myapp.svg

import de.ilijaz.myapp.myapp.icon.Icon
import de.ilijaz.myapp.myapp.vectorgraphic.VectorGraphic
import java.util.*

class IconToSvgConverter(
    private val icon: Icon,
    private val dimensions: Int,
) {
    companion object {
        fun indentBy(input: String, increments: Int) = input.lines().joinToString("\n") { "  ".repeat(increments) + it }
    }

    private val mainIconMaskId = UUID.randomUUID().toString()

    fun render(): String {
        val result = ArrayList<String>()
        result.add("<svg width=\"$dimensions\" height=\"$dimensions\" viewBox=\"0 0 $dimensions $dimensions\">")
        result.add("  <defs>")
        if (icon.backgroundIcon?.mask == true) {
            result.add(indentBy(getBaseIconMask(), 2))
        }
        result.add("  </defs>")
        if (!(icon.backgroundIcon?.mask == true)) {
            result.add(indentBy(getBaseIcon(), 1))
        }
        if (icon.backgroundIcon != null) {
            result.add(indentBy(getBackgroundIcon(), 1))
        }
        result.add("</svg>")
        return result.joinToString("\n")
    }

    private fun getBaseIconMask(): String {
        val result = ArrayList<String>()
        result.add("<mask id=\"$mainIconMaskId\">")
        result.add("  <rect width=\"100%\" height=\"100%\" fill=\"white\" />")
        result.add(indentBy(getBaseIcon(), 1))
        result.add("</mask>")
        return result.joinToString("\n")
    }

    private fun getBaseIcon(): String {
        val result = ArrayList<String>()
        val transform = createTransform(icon.backgroundIcon)
        result.add("<g transform-origin=\"center\" $transform>")
        result.add(indentBy(icon.baseIcon.paths, 1))
        if (icon.additionalIcon != null) {
            result.add(indentBy(icon.additionalIcon.paths, 1))
        }
        result.add("</g>")
        return result.joinToString("\n")
    }

    private fun createTransform(vectorGraphic: VectorGraphic?): String = if (vectorGraphic == null) ""
    else " transform=\"rotate(${vectorGraphic.rotation}) translate(${vectorGraphic.translationX} ${vectorGraphic.translationY}) scale(${vectorGraphic.scale})\""

    private fun getBackgroundIcon(): String {
        val result = ArrayList<String>()
        val mask = if (icon.backgroundIcon?.mask == true) " mask=\"url(#$mainIconMaskId)\"" else ""
        result.add("<g$mask>")
        result.add(indentBy(icon.backgroundIcon!!.paths, 1))
        result.add("</g>")
        return result.joinToString("\n")
    }
}