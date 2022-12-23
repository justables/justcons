package de.ilijaz.myapp.myapp.icon.domain

import de.ilijaz.myapp.myapp.icon.IconStackPosition
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "icon_stacks")
class IconStack(
    @Id @GeneratedValue(strategy = GenerationType.AUTO) var id: UUID?,
    @OneToMany(mappedBy = "iconStack") val iconLayers: List<IconLayer>,
    val position: IconStackPosition,
    @ManyToOne @JoinColumn(name = "icon") val icon: Icon,
)