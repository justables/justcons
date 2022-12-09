package de.ilijaz.myapp.core.crudservice

import org.springframework.data.repository.CrudRepository

abstract class CrudService<Entity, DTO, ID>(
  private val mapper: EntityMapper<Entity, DTO>,
  private val repository: CrudRepository<Entity, ID>,
) {
  fun findAll(): Iterable<DTO> {
    return mapper.toDTO(repository.findAll())
  }

  fun find(id: ID): DTO? {
    return mapper.toDTO(repository.findById(id).orElse(null))
  }

  fun save(dto: DTO): DTO {
    return mapper.toDTO(repository.save(mapper.fromDTO(dto)))
  }

  fun delete(id: ID) {
    repository.deleteById(id)
  }
}
