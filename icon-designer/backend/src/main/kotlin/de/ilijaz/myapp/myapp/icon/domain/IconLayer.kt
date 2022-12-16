package de.ilijaz.myapp.myapp.icon.domain

import de.ilijaz.myapp.myapp.vectorgraphic.VectorGraphic
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "icon_layers")
class IconLayer(
    @Id val id: UUID,
    @ManyToOne val vectorGraphic: VectorGraphic?,
    val sortPosition: Int,
    @ManyToOne @JoinColumn(name = "iconStack") val iconStack: IconStack? = null,
)