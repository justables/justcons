package de.ilijaz.myapp.myapp.cat

import de.ilijaz.myapp.core.crudservice.EntityMapper
import de.ilijaz.myapp.myapp.name.NameDTO
import org.springframework.stereotype.Component

@Component
class CatMapper : EntityMapper<Cat, CatDTO>() {
    override fun toDTO(entity: Cat): CatDTO = CatDTO(
        id = entity.id,
        name = NameDTO(entity.name, entity.name),
        age = entity.age,
        imageUrl = entity.imageUrl,
    )

    override fun fromDTO(dto: CatDTO): Cat = Cat(
        id = dto.id,
        name = dto.name.fullName,
        age = dto.age,
        imageUrl = dto.imageUrl,
    )
}
