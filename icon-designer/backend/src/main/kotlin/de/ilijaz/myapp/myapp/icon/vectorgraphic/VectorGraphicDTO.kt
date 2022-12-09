package de.ilijaz.myapp.myapp.icon.vectorgraphic

import java.util.*

data class VectorGraphicDTO(
    val id: UUID,
    val name: String,
    val vectorGraphic: String,
    val type: VectorGraphicType,
)