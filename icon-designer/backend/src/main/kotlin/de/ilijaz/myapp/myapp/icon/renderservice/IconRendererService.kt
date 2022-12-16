package de.ilijaz.myapp.myapp.icon.renderservice

import de.ilijaz.myapp.myapp.icon.domain.Icon
import de.ilijaz.myapp.myapp.icon.domain.IconStack
import de.ilijaz.myapp.myapp.vectorgraphic.VectorGraphic
import org.springframework.stereotype.Service
import java.util.*

@Service
class IconRendererService {
    companion object {
        fun indentBy(input: String, increments: Int) = input.lines().joinToString("\n") { "  ".repeat(increments) + it }
        fun indentBy(lines: List<String>, amount: Int): List<String> = lines.map { "  ".repeat(amount) + it }

    }

    fun render(icon: Icon, dimensions: Int): String {
        val result = ArrayList<String>()
        val groups = ArrayList<IconRendererGroup>()
        icon.iconStack.map { renderStack(it, groups) }
        result.add("<svg xmlns:xlink=\"http://www.w3.org/1999/xlink\" xmlns=\"http://www.w3.org/2000/svg\" version=\"1.0\" width=\"$dimensions\" height=\"$dimensions\" viewBox=\"0 0 $dimensions $dimensions\">")
        result.add("  <defs>")
        result.addAll(indentBy(renderDefs(groups), 3))
        result.add("  </defs>")
        result.addAll(indentBy(renderGroups(groups), 2))
        result.add("</svg>")
        return result.joinToString("\n")
    }

    private fun renderStack(iconStack: IconStack, groups: ArrayList<IconRendererGroup>) {
        var groupType = IconRendererGroupType.Group
        var lastId: String? = null
        iconStack.iconLayer.forEach { iconLayer ->
            val id = UUID.randomUUID().toString()
            val vectorGraphic: VectorGraphic = iconLayer.vectorGraphic ?: return
            groups.add(
                IconRendererGroup(
                    groupType,
                    listOf("<[GroupTagName] id=\"$id\">", "  " + vectorGraphic.paths, "</[GroupTagName]>")
                )
            )
            lastId = id
            if (vectorGraphic.mask) {
                groupType =
                    if (groupType == IconRendererGroupType.Group) IconRendererGroupType.Mask else IconRendererGroupType.Group
            }
        }
    }

    private fun renderDefs(groups: ArrayList<IconRendererGroup>): List<String> {
        val result = ArrayList<String>()
        groups.filter { group -> group.type == IconRendererGroupType.ClipRect }.forEach {
            result.addAll(it.lines.map { line -> line.replace("[GroupTagName]", "clipPath") })
        }
        groups.filter { group -> group.type == IconRendererGroupType.Mask }.forEach {
            result.addAll(it.lines.map { line -> line.replace("[GroupTagName]", "mask") })
        }
        return result
    }

    private fun renderGroups(groups: ArrayList<IconRendererGroup>): List<String> {
        val result = ArrayList<String>()
        groups.filter { group -> group.type == IconRendererGroupType.Group }.forEach {
            result.addAll(it.lines.map { line -> line.replace("[GroupTagName]", "g") })
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