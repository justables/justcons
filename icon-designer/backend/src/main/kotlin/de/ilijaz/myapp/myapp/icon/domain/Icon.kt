package de.ilijaz.myapp.myapp.icon.domain

import java.util.*
import javax.persistence.*

@Entity
@Table(name = "icons")
data class Icon(
    @Id @GeneratedValue(strategy = GenerationType.AUTO) var id: UUID?,
    val name: String,
    val dimensions: Int,
    @OneToMany(mappedBy = "icon", cascade = [CascadeType.ALL]) val iconStacks: List<IconStack>,
)