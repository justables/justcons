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
        type = entity.type,
        xTranslation = entity.xTranslation,
        yTranslation = entity.yTranslation,
        scale = entity.scale,
        rotation = entity.rotation,
    )

    override fun fromDTO(dto: VectorGraphicDTO): VectorGraphic = VectorGraphic(
        id = dto.id ?: UUID.randomUUID(),
        name = dto.name,
        paths = dto.paths,
        type = dto.type,
        transformation = VectorGraphic.createTransformation(dto.xTranslation, dto.yTranslation, dto.scale, dto.rotation)
    )
}