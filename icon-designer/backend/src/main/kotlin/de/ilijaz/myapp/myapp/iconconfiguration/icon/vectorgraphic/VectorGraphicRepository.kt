package de.ilijaz.myapp.myapp.iconconfiguration.icon.vectorgraphic

import org.springframework.data.repository.CrudRepository
import java.util.UUID

interface VectorGraphicRepository : CrudRepository<VectorGraphic, UUID> {
    fun findByName(name: String): VectorGraphic?
}
