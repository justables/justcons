package de.ilijaz.myapp.myapp.icon.renderer

import de.ilijaz.myapp.myapp.icon.domain.IconStack

class IconStackRenderer(
    private val iconStack: IconStack,
    private val dimensions: Int,
) {
    fun render(): IconGroupsAndDefs {
        if (iconStack.iconLayer.isEmpty()) {
            return IconGroupsAndDefs(emptyList(), emptyList())
        }
        return IconLayerRenderer(
            iconStack.iconLayer.first(),
            dimensions,
            iconStack.iconLayer.toMutableList().let { it.remove(it.first()); it },
        ).render()
    }
}