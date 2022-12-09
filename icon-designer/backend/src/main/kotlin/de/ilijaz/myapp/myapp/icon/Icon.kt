package de.ilijaz.myapp.myapp.icon

import de.ilijaz.myapp.myapp.icon.svg.Svg
import de.ilijaz.myapp.myapp.icon.vectorgraphic.VectorGraphic

data class Icon(
    val baseIcon: VectorGraphic,
    val additionalIcon: VectorGraphic?,
    val backgroundIcon: VectorGraphic?,
    val upperRightCornerIcon: VectorGraphic?,
    val upperRightCornerIconBackground: VectorGraphic?,
    val lowerRightCornerIcon: VectorGraphic?,
    val lowerRightCornerIconBackground: VectorGraphic?,
    val lowerLeftCornerIcon: VectorGraphic?,
    val lowerLeftCornerIconBackground: VectorGraphic?,
    val upperLeftCornerIcon: VectorGraphic?,
    val upperLeftCornerIconBackground: VectorGraphic?,
) {
    fun render(): String = Svg(this, 24).render()
}