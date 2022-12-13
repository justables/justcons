package de.ilijaz.myapp.myapp.vectorgraphic

import de.ilijaz.myapp.core.crudservice.EntityMapper
import org.springframework.stereotype.Component
import java.util.*

@Component
class VectorGraphicMapper : EntityMapper<VectorGraphic, VectorGraphicDTO>() {
    override fun toDTO(entity: VectorGraphic): VectorGraphicDTO = VectorGraphicDTO(
        id = entity.id,
        name = entity.name,
        paths = entity.paths,
        translationX = entity.translationX,
        translationY = entity.translationY,
        scale = entity.scale,
        rotation = entity.rotation,
        mask = entity.mask,
    )

    override fun fromDTO(dto: VectorGraphicDTO): VectorGraphic = VectorGraphic(
        id = dto.id ?: UUID.randomUUID(),
        name = dto.name,
        paths = dto.paths,
        translationX = dto.translationX,
        translationY = dto.translationY,
        scale = dto.scale,
        rotation = dto.rotation,
        mask = dto.mask,
    )
}