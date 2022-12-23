package de.ilijaz.myapp.myapp.icon.repository

import de.ilijaz.myapp.myapp.icon.domain.Icon
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import java.util.*

interface IconRepository : CrudRepository<Icon, UUID> {
    @Query("select i from Icon i where i.name = :name")
    fun findByName(@Param("name") name: String): Icon?
}
