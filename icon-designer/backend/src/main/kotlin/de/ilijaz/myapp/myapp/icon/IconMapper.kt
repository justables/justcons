package de.ilijaz.myapp.myapp.icon

import de.ilijaz.myapp.core.crudservice.EntityMapper
import de.ilijaz.myapp.myapp.icon.domain.Icon
import de.ilijaz.myapp.myapp.icon.domain.IconLayer
import de.ilijaz.myapp.myapp.icon.domain.IconStack
import de.ilijaz.myapp.myapp.icon.dto.IconDTO
import de.ilijaz.myapp.myapp.icon.dto.IconLayerDTO
import de.ilijaz.myapp.myapp.icon.dto.IconStackDTO
import de.ilijaz.myapp.myapp.icon.renderservice.IconRendererService
import de.ilijaz.myapp.myapp.svg.SvgToPngConverter
import de.ilijaz.myapp.myapp.vectorgraphic.VectorGraphicMapper
import org.springframework.stereotype.Component
import java.util.*

@Component
class IconMapper(
    private val vectorGraphicMapper: VectorGraphicMapper,
    private val iconRenderService: IconRendererService
) : EntityMapper<Icon, IconDTO>() {
    override fun toDTO(entity: Icon): IconDTO = IconDTO(
        id = entity.id,
        name = entity.name,
        image = SvgToPngConverter.svgToPng(iconRenderService.render(entity, 32)),
        iconStack = entity.iconStack.map { iconStack ->
            IconStackDTO(
                id = iconStack.id,
                position = iconStack.position,
                image = SvgToPngConverter.svgToPng(iconRenderService.render(getIconFromIconStack(iconStack), 32)),
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

    private fun getIconFromIconStack(iconStack: IconStack) = Icon(
        id = UUID.randomUUID(),
        name = "tmp",
        iconStack = listOf(iconStack)
    )
}