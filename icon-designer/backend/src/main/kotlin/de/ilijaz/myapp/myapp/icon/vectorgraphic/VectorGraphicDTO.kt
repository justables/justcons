package de.ilijaz.myapp.myapp.icon.vectorgraphic

import java.util.*

data class VectorGraphicDTO(
    val id: UUID,
    val name: String,
    val paths: String,
    val type: VectorGraphicType,
    val xTranslation: Float,
    val yTranslation: Float,
    val scale: Float,
    val rotation: Float,
    val svg: String?,
)