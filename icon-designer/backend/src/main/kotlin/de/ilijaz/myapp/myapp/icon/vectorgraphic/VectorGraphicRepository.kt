package de.ilijaz.myapp.myapp.icon.vectorgraphic

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import java.util.*

interface VectorGraphicRepository : CrudRepository<VectorGraphic, UUID> {
    @Query("select vg from VectorGraphic vg where vg.name = :name")
    fun findByName(@Param("name") name: String): List<VectorGraphic>
}
