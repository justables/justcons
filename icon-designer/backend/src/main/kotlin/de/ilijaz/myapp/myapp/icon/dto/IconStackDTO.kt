package de.ilijaz.myapp.myapp.icon.dto

import de.ilijaz.myapp.myapp.icon.IconStackPosition
import java.util.*

class IconStackDTO(
    val id: UUID?,
    val iconLayer: List<IconLayerDTO>,
    val position: IconStackPosition,
    val image: ByteArray? = null,
)