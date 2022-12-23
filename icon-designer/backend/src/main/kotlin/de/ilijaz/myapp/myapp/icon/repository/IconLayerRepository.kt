package de.ilijaz.myapp.myapp.icon.repository

import de.ilijaz.myapp.myapp.icon.domain.IconLayer
import org.springframework.data.repository.CrudRepository
import java.util.*

interface IconLayerRepository : CrudRepository<IconLayer, UUID>
