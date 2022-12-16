package de.ilijaz.myapp.myapp.icon

import de.ilijaz.myapp.myapp.icon.dto.IconDTO
import de.ilijaz.myapp.myapp.icon.dto.IconStackDTO
import de.ilijaz.myapp.myapp.icon.renderservice.IconRendererService
import de.ilijaz.myapp.myapp.svg.SvgToPngConverter
import org.springframework.stereotype.Service
import java.util.*

@Service
class IconService(
    private val iconRepository: IconRepository,
    private val iconMapper: IconMapper,
    private val iconRenderService: IconRendererService
) {
    fun findAll(): Iterable<IconDTO> = iconMapper.toDTO(iconRepository.findAll())

    fun findByName(name: String): IconDTO? {
        val icon = iconRepository.findByName(name)
        return if (icon != null) iconMapper.toDTO(icon) else null
    }

    fun save(iconDTO: IconDTO): IconDTO =
        iconMapper.toDTO(iconRepository.save(iconMapper.fromDTO(iconDTO)))

    fun save(iconDTOs: List<IconDTO>): List<IconDTO> =
        iconDTOs.map { save(it) }

    fun delete(iconDTO: IconDTO) {
        if (iconDTO.id == null) {
            throw IllegalArgumentException("IconDTO has no id")
        }
        iconRepository.deleteById(iconDTO.id)
    }

    fun delete(iconDTOs: List<IconDTO>) = iconDTOs.forEach { delete(it) }

    fun renderStack(iconStack: IconStackDTO): ByteArray {
        val iconDTO = IconDTO(
            id = UUID.randomUUID(),
            name = "tmp",
            iconStack = listOf(iconStack)
        )
        val icon = iconMapper.fromDTO(iconDTO)
        val svg = iconRenderService.render(icon, 24)
        return SvgToPngConverter.svgToPng(svg)
    }
}