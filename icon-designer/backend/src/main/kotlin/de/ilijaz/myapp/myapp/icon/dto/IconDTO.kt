package de.ilijaz.myapp.myapp.icon.dto

import java.util.*

class IconDTO(
    val id: UUID?,
    val name: String,
    val dimensions: Int,
    val iconStacks: List<IconStackDTO>,
    val image: ByteArray? = null,
)