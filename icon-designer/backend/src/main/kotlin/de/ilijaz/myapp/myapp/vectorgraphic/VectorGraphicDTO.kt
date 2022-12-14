package de.ilijaz.myapp.myapp.vectorgraphic

import java.util.*

data class VectorGraphicDTO(
    val id: UUID?,
    val name: String,
    val paths: String,
    val dimensions: Float,
    val translationX: Float,
    val translationY: Float,
    val scale: Float,
    val rotation: Float,
    val mask: Boolean,
    var image: ByteArray? = null,
)