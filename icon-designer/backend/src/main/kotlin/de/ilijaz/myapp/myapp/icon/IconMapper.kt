package de.ilijaz.myapp.myapp.icon

import de.ilijaz.myapp.core.crudservice.EntityMapper
import de.ilijaz.myapp.myapp.icon.domain.Icon
import de.ilijaz.myapp.myapp.icon.domain.IconLayer
import de.ilijaz.myapp.myapp.icon.domain.IconStack
import de.ilijaz.myapp.myapp.icon.dto.IconDTO
import de.ilijaz.myapp.myapp.icon.dto.IconLayerDTO
import de.ilijaz.myapp.myapp.icon.dto.IconStackDTO
import de.ilijaz.myapp.myapp.vectorgraphic.VectorGraphicMapper
import org.springframework.stereotype.Component
import java.util.*

@Component
class IconMapper(
    private val vectorGraphicMapper: VectorGraphicMapper,
) : EntityMapper<Icon, IconDTO>() {
    override fun toDTO(entity: Icon): IconDTO = IconDTO(
        id = entity.id,
        name = entity.name,
        iconStack = entity.iconStack.map { iconStack ->
            IconStackDTO(
                id = iconStack.id,
                position = iconStack.position,
                iconLayer = iconStack.iconLayer.map { iconLayer ->
                    IconLayerDTO(
                        id = iconLayer.id,
                        vectorGraphic = iconLayer.vectorGraphic?.let { vectorGraphicMapper.toDTO(it) },
                        sortPosition = iconLayer.sortPosition,
                    )
                }
            )
        }
    )

    override fun fromDTO(dto: IconDTO): Icon = Icon(
        id = dto.id ?: UUID.randomUUID(),
        name = dto.name,
        iconStack = dto.iconStack.map { iconStack ->
            IconStack(
                id = iconStack.id ?: UUID.randomUUID(),
                position = iconStack.position,
                iconLayer = iconStack.iconLayer.map { iconLayer ->
                    IconLayer(
                        id = iconLayer.id ?: UUID.randomUUID(),
                        vectorGraphic = iconLayer.vectorGraphic?.let { vectorGraphicMapper.fromDTO(it) },
                        sortPosition = iconLayer.sortPosition,
                    )
                }
            )
        }
    )
}