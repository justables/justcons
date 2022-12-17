package de.ilijaz.myapp.myapp.icon.renderservice

import de.ilijaz.myapp.myapp.icon.domain.Icon
import de.ilijaz.myapp.myapp.icon.domain.IconStack
import de.ilijaz.myapp.myapp.vectorgraphic.VectorGraphic
import org.springframework.stereotype.Service
import java.util.*
import kotlin.math.sin

@Service
class IconRendererService {
    companion object {
        fun indentBy(input: String, increments: Int) = input.lines().joinToString("\n") { "  ".repeat(increments) + it }
        fun indentBy(lines: List<String>, amount: Int): List<String> = lines.map { "  ".repeat(amount) + it }

    }

    fun render(icon: Icon, dimensions: Int): String {
        val result = ArrayList<String>()
        val groups = ArrayList<IconRendererGroup>()
        icon.iconStack.map { renderStack(it, groups, dimensions) }
        result.add("<svg xmlns:xlink=\"http://www.w3.org/1999/xlink\" xmlns=\"http://www.w3.org/2000/svg\" version=\"1.0\" width=\"$dimensions\" height=\"$dimensions\" viewBox=\"0 0 $dimensions $dimensions\">")
        result.add("  <defs>")
        result.addAll(indentBy(renderDefs(groups), 3))
        result.add("  </defs>")
        result.addAll(indentBy(renderGroups(groups), 2))
        result.add("</svg>")
        return result.joinToString("\n")
    }

    private fun renderStack(iconStack: IconStack, groups: ArrayList<IconRendererGroup>, dimensions: Int) {
        var groupType = IconRendererGroupType.Group
        var lastGroupType = IconRendererGroupType.Group
        var lastVectorGraphic: VectorGraphic? = null
        var id = UUID.randomUUID().toString()
        iconStack.iconLayer.forEach { iconLayer ->
            val vectorGraphic: VectorGraphic = iconLayer.vectorGraphic ?: return
            var attributes = ""
            if (vectorGraphic.mask) {
                id = UUID.randomUUID().toString()
                attributes += " mask=\"url(#$id)\""
            }
            if (lastVectorGraphic != null) {
                val offset = Math.sqrt(2.0) * dimensions * 3 / 8
                val rotation = lastVectorGraphic!!.rotation
                val translationX =
                    lastVectorGraphic!!.translationX + sin(Math.toRadians(rotation.toDouble() - 45)) * dimensions * 3 / 4 + offset

                val translationY =
                    lastVectorGraphic!!.translationX + sin(Math.toRadians(rotation.toDouble() - 135)) * dimensions * 3 / 4 + offset
                attributes += " transform=\"translate($translationX, $translationY) scale(${lastVectorGraphic!!.scale}) rotate($rotation)\""
            }
            groups.add(
                IconRendererGroup(
                    id = id, lastGroupType, listOf("<g$attributes>", "  " + vectorGraphic.paths, "</g>")
                )
            )
            if (vectorGraphic.mask) {
                groupType =
                    if (groupType == IconRendererGroupType.Group) IconRendererGroupType.Mask else IconRendererGroupType.Group
            }
            lastGroupType = groupType
            lastVectorGraphic = vectorGraphic
        }
    }

    private fun renderDefs(groups: ArrayList<IconRendererGroup>): List<String> {
        val result = ArrayList<String>()
        groups.filter { group -> group.type == IconRendererGroupType.ClipRect }.groupBy { group -> group.id }
            .forEach { (id, groups) ->
                result.add("<clipPath id=\"$id\">")
                groups.map { group -> indentBy(group.lines, 1) }.forEach { lines -> result.addAll(lines) }
                result.add("</clipPath>")
            }
        groups.filter { group -> group.type == IconRendererGroupType.Mask }.groupBy { group -> group.id }
            .forEach { (id, groups) ->
                result.add("<mask id=\"$id\">")
                result.add("  <rect width=\"100%\" height=\"100%\" fill=\"white\" />")
                groups.map { group -> indentBy(group.lines, 1) }.forEach { lines ->
                    result.addAll(lines.map { line ->
                        line.replace(
                            Regex("(style=\"[#:;\\-a-zA-Z0-9]+\"|(fill|stroke)=\"#?[a-zA-Z0-ß]+\")"), ""
                        )
                    })
                }
                result.add("</mask>")
            }
        return result
    }

    private fun renderGroups(groups: ArrayList<IconRendererGroup>): List<String> {
        val result = ArrayList<String>()
        groups.filter { group -> group.type == IconRendererGroupType.Group }.forEach {
            result.addAll(it.lines)
        }
        return result
    }

//    fun render(): String {
//        val result = ArrayList<String>()
//        result.add("<svg width=\"$dimensions\" height=\"$dimensions\" viewBox=\"0 0 $dimensions $dimensions\">")
//        result.add("  <defs>")
//        if (icon.backgroundIcon?.mask == true) {
//            result.add(indentBy(getBaseIconMask(), 2))
//        }
//        result.add("  </defs>")
//        if (!(icon.backgroundIcon?.mask == true)) {
//            result.add(indentBy(getBaseIcon(), 1))
//        }
//        if (icon.backgroundIcon != null) {
//            result.add(indentBy(getBackgroundIcon(), 1))
//        }
//        result.add("</svg>")
//        return result.joinToString("\n")
//    }
//
//    private fun getBaseIconMask(): String {
//        val result = ArrayList<String>()
//        result.add("<mask id=\"$mainIconMaskId\">")
//        result.add("  <rect width=\"100%\" height=\"100%\" fill=\"white\" />")
//        result.add(indentBy(getBaseIcon(), 1))
//        result.add("</mask>")
//        return result.joinToString("\n")
//    }
//
//    private fun getBaseIcon(): String {
//        val result = ArrayList<String>()
//        val transform = createTransform(icon.backgroundIcon)
//        result.add("<g transform-origin=\"center\" $transform>")
//        result.add(indentBy(icon.baseIcon.paths, 1))
//        if (icon.additionalIcon != null) {
//            result.add(indentBy(icon.additionalIcon.paths, 1))
//        }
//        result.add("</g>")
//        return result.joinToString("\n")
//    }
//
//    private fun createTransform(vectorGraphic: VectorGraphic?): String = if (vectorGraphic == null) ""
//    else " transform=\"rotate(${vectorGraphic.rotation}) translate(${vectorGraphic.translationX} ${vectorGraphic.translationY}) scale(${vectorGraphic.scale})\""
//
//    private fun getBackgroundIcon(): String {
//        val result = ArrayList<String>()
//        val mask = if (icon.backgroundIcon?.mask == true) " mask=\"url(#$mainIconMaskId)\"" else ""
//        result.add("<g$mask>")
//        result.add(indentBy(icon.backgroundIcon!!.paths, 1))
//        result.add("</g>")
//        return result.joinToString("\n")
//    }

}