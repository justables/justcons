package de.ilijaz.myapp.myapp.icon

import de.ilijaz.myapp.core.crudservice.EntityMapper
import de.ilijaz.myapp.myapp.icon.domain.Icon
import de.ilijaz.myapp.myapp.icon.domain.IconLayer
import de.ilijaz.myapp.myapp.icon.domain.IconStack
import de.ilijaz.myapp.myapp.icon.dto.IconDTO
import de.ilijaz.myapp.myapp.icon.dto.IconLayerDTO
import de.ilijaz.myapp.myapp.icon.dto.IconStackDTO
import de.ilijaz.myapp.myapp.icon.renderer.IconRendererService
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
        image = SvgToPngConverter.svgToPng(iconRenderService.render(entity)),
        iconStack = entity.iconStacks.map { iconStack ->
            IconStackDTO(
                id = iconStack.id,
                position = iconStack.position,
                image = SvgToPngConverter.svgToPng(iconRenderService.render(getIconFromIconStack(iconStack))),
                iconLayer = iconStack.iconLayers.map { iconLayer ->
                    IconLayerDTO(
                        id = iconLayer.id,
                        vectorGraphic = iconLayer.vectorGraphic?.let { vectorGraphicMapper.toDTO(it) },
                        sortPosition = iconLayer.sortPosition,
                    )
                }
            )
        }
    )

    override fun fromDTO(dto: IconDTO): Icon {
        val iconStacks = mutableListOf<IconStack>()
        val icon = Icon(
            id = dto.id,
            name = dto.name,
            iconStacks = iconStacks
        )
        iconStacks.addAll(dto.iconStack.map { iconStack ->
            val iconLayer = mutableListOf<IconLayer>()
            val iconStackResult = IconStack(
                id = iconStack.id,
                position = iconStack.position,
                iconLayers = iconLayer,
                icon = icon,
            )
            iconLayer.addAll(iconStack.iconLayer.map { iconLayer1 ->
                IconLayer(
                    id = iconLayer1.id,
                    vectorGraphic = iconLayer1.vectorGraphic?.let { vectorGraphicMapper.fromDTO(it) },
                    sortPosition = iconLayer1.sortPosition,
                    iconStack = iconStackResult,
                )
            })
            iconStackResult
        })
        return icon
    }

    private fun getIconFromIconStack(iconStack: IconStack): Icon {
        val iconStacks = mutableListOf<IconStack>()
        val icon = Icon(
            id = UUID.randomUUID(),
            name = "tmp",
            iconStacks = iconStacks
        )
        iconStacks.add(
            IconStack(
                id = UUID.randomUUID(),
                position = IconStackPosition.Center,
                iconLayers = iconStack.iconLayers,
                icon = icon,
            )
        )
        return icon
    }
}