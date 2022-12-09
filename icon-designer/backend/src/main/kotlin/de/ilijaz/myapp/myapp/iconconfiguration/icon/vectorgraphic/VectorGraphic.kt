package de.ilijaz.myapp.myapp.iconconfiguration.icon.vectorgraphic

import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "vector_graphics")
class VectorGraphic(
        @Id
        val id: UUID,
        val name: String,
        @Column(columnDefinition="TEXT")
        val vectorGraphic: String,
)