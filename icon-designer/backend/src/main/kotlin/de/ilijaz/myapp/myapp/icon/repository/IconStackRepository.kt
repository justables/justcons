package de.ilijaz.myapp.myapp.icon.repository

import de.ilijaz.myapp.myapp.icon.domain.IconStack
import org.springframework.data.repository.CrudRepository
import java.util.*

interface IconStackRepository : CrudRepository<IconStack, UUID>
