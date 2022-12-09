package de.ilijaz.myapp.core.crudservice

import java.util.*

abstract class EntityMapper<Entity, DTO> {
  abstract fun toDTO(entity: Entity): DTO
  fun toDTO(entities: Iterable<Entity>): Iterable<DTO> = entities.map { toDTO(it) }
  fun toDTO(entity: Optional<Entity>): Optional<DTO> =
    if (entity.isPresent) Optional.of(toDTO(entity.get())) else Optional.empty()

  abstract fun fromDTO(dto: DTO): Entity
  fun fromDTO(dtos: Iterable<DTO>): Iterable<Entity> = dtos.map { fromDTO(it) }
  fun fromDTO(dto: Optional<DTO>): Optional<Entity> =
    if (dto.isPresent) Optional.of(fromDTO(dto.get())) else Optional.empty()

}
