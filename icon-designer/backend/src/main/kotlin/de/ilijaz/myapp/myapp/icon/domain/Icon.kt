package de.ilijaz.myapp.myapp.icon.domain

import java.util.*
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "icons")
data class Icon(
    @Id val id: UUID,
    val name: String,
    val dimensions: Int = 24,
    @OneToMany(mappedBy = "icon") val iconStack: List<IconStack>,
)