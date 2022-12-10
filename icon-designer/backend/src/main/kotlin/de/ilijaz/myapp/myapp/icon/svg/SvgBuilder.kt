package de.ilijaz.myapp.myapp.icon.svg

import de.ilijaz.myapp.myapp.icon.Icon
import de.ilijaz.myapp.myapp.icon.vectorgraphic.VectorGraphic
import de.ilijaz.myapp.myapp.icon.vectorgraphic.VectorGraphicType
import java.util.*

data class SvgBuilder(
    val icon: Icon,
    val dimensions: Int,
) {
    private val mainIconMaskId = UUID.randomUUID().toString()

    fun render(): String {
        val result = ArrayList<String>()
        result.add("<svg width=\"$dimensions\" height=\"$dimensions\" viewBox=\"0 0 $dimensions $dimensions\">")
        result.add("  <defs>")
        if (isMainIconMask()) {
            result.add(incrementBy(getBaseIconMask(), 2))
        }
        result.add("  </defs>")
        if (!isMainIconMask()) {
            result.add(incrementBy(getBaseIcon(), 1))
        }
        if (icon.backgroundIcon != null) {
            result.add(incrementBy(getBackgroundIcon(), 1))
        }
        result.add("</svg>")
        return result.joinToString("\n")
    }

    private fun isMainIconMask(): Boolean = icon.backgroundIcon?.type == VectorGraphicType.Filled

    private fun getBaseIconMask(): String {
        val result = ArrayList<String>()
        result.add("<mask id=\"$mainIconMaskId\">")
        result.add("  <rect width=\"100%\" height=\"100%\" fill=\"white\" />")
        result.add(incrementBy(getBaseIcon(), 1))
        result.add("</mask>")
        return result.joinToString("\n")
    }

    private fun getBaseIcon(): String {
        val result = ArrayList<String>()
        val transform = createTransform(icon.backgroundIcon)
        result.add("<g transform-origin=\"center\" $transform>")
        result.add(incrementBy(getIconPaths(icon.baseIcon), 1))
        if (icon.additionalIcon != null) {
            result.add(incrementBy(getIconPaths(icon.additionalIcon), 1))
        }
        result.add("</g>")
        return result.joinToString("\n")
    }

    private fun createTransform(vectorGraphic: VectorGraphic?): String = if (vectorGraphic == null) ""
    else " transform=\"rotate(${vectorGraphic.rotation}) translate(${vectorGraphic.xTranslation} ${vectorGraphic.yTranslation}) scale(${vectorGraphic.scale})\""

    private fun getIconPaths(vectorGraphic: VectorGraphic): String =
        vectorGraphic.paths.split(";").joinToString("\n") { "<path d=\"$it\" color=\"currentColor\" />" }

    private fun getBackgroundIcon(): String {
        val result = ArrayList<String>()
        val mask = if (isMainIconMask()) " mask=\"url(#$mainIconMaskId)\"" else ""
        result.add("<g$mask>")
        result.add(incrementBy(getIconPaths(icon.backgroundIcon!!), 1))
        result.add("</g>")
        return result.joinToString("\n")
    }

    private fun incrementBy(input: String, increments: Int) =
        input.lines().joinToString("\n") { "  ".repeat(increments) + it }
}