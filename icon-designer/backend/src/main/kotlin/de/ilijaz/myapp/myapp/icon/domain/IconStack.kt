package de.ilijaz.myapp.myapp.icon.domain

import de.ilijaz.myapp.myapp.icon.IconStackPosition
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "icon_stacks")
class IconStack(
    @Id val id: UUID,
    @OneToMany(mappedBy = "iconStack") val iconLayer: List<IconLayer>,
    val position: IconStackPosition,
    @ManyToOne @JoinColumn(name = "icon") val icon: Icon? = null,
)