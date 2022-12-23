package de.ilijaz.myapp.myapp.icon.renderer

import de.ilijaz.myapp.myapp.icon.domain.Icon

class IconRenderer(
    private val icon: Icon,
) {

    fun render(): String {
        val lines = ArrayList<String>()
        lines.add(
            "<svg " +
                "xmlns:xlink=\"http://www.w3.org/1999/xlink\" " +
                "xmlns=\"http://www.w3.org/2000/svg\" " +
                "version=\"1.0\" " +
                "width=\"${icon.dimensions}\" height=\"${icon.dimensions}\" " +
                "viewBox=\"0 0 ${icon.dimensions} ${icon.dimensions}\">"
        )
        val renderedStacks = renderStacks()
        lines.add(IconRendererUtil.indentBy(1, "<defs>"))
        lines.addAll(IconRendererUtil.indentBy(2, renderedStacks.defs))
        lines.add(IconRendererUtil.indentBy(1, "</defs>"))
        lines.addAll(IconRendererUtil.indentBy(1, renderedStacks.groups))
        lines.add("</svg>")
        return lines.joinToString("\n")
    }

    private fun renderStacks(): IconGroupsAndDefs {
        val iconStack: List<IconGroupsAndDefs> = icon.iconStacks.map { IconStackRenderer(it, icon.dimensions).render() }
        return IconGroupsAndDefs(
            iconStack.flatMap { it.defs },
            iconStack.flatMap { it.groups },
        )
    }
}