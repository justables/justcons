package de.ilijaz.myapp.myapp.icon.vectorgraphic

import de.ilijaz.myapp.core.crudservice.EntityMapper
import org.springframework.stereotype.Component

@Component
class VectorGraphicMapper : EntityMapper<VectorGraphic, VectorGraphicDTO>() {
    override fun toDTO(entity: VectorGraphic): VectorGraphicDTO = VectorGraphicDTO(
        entity.id, entity.name, entity.vectorGraphic, entity.type,
    )

    override fun fromDTO(dto: VectorGraphicDTO): VectorGraphic = VectorGraphic(
        dto.id, dto.name, dto.vectorGraphic, dto.type,
    )
}