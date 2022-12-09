package de.ilijaz.myapp.myapp.cat

import de.ilijaz.myapp.core.crudservice.CrudService
import org.springframework.stereotype.Service

@Service
class CatService(
  private val catMapper: CatMapper,
  private val catRepository: CatRepository,
) : CrudService<Cat, CatDTO, Int>(
  mapper = catMapper,
  repository = catRepository,
)
