package de.ilijaz.myapp.myapp.icon

import de.ilijaz.myapp.myapp.icon.vectorgraphic.VectorGraphicSelectorDTO

data class IconDTO(
    val baseIcon: VectorGraphicSelectorDTO,
    val additionalIcon: VectorGraphicSelectorDTO?,
    val backgroundIcon: VectorGraphicSelectorDTO?,
    val upperRightCornerIcon: VectorGraphicSelectorDTO?,
    val upperRightCornerIconBackground: VectorGraphicSelectorDTO?,
    val lowerRightCornerIcon: VectorGraphicSelectorDTO?,
    val lowerRightCornerIconBackground: VectorGraphicSelectorDTO?,
    val lowerLeftCornerIcon: VectorGraphicSelectorDTO?,
    val lowerLeftCornerIconBackground: VectorGraphicSelectorDTO?,
    val upperLeftCornerIcon: VectorGraphicSelectorDTO?,
    val upperLeftCornerIconBackground: VectorGraphicSelectorDTO?
)