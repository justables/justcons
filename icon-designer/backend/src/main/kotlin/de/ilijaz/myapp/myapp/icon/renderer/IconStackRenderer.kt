package de.ilijaz.myapp.myapp.icon.renderer

import de.ilijaz.myapp.myapp.icon.domain.IconStack

class IconStackRenderer(
    private val iconStack: IconStack,
    private val dimensions: Int,
) {
    fun render(): IconGroupsAndDefs {
        if (iconStack.iconLayers.isEmpty()) {
            return IconGroupsAndDefs(emptyList(), emptyList())
        }
        val sortedIconLayers = iconStack.iconLayers.sortedBy { it.sortPosition }
        return IconLayerRenderer(
            sortedIconLayers.first(),
            dimensions,
            sortedIconLayers.toMutableList().let { it.remove(it.first()); it },
        ).render()
    }
}