package de.ilijaz.myapp.myapp.icon

import de.ilijaz.myapp.myapp.icon.dto.IconDTO
import de.ilijaz.myapp.myapp.icon.repository.IconLayerRepository
import de.ilijaz.myapp.myapp.icon.repository.IconRepository
import de.ilijaz.myapp.myapp.icon.repository.IconStackRepository
import org.springframework.stereotype.Service

@Service
class IconService(
    private val iconRepository: IconRepository,
    private val iconStackRepository: IconStackRepository,
    private val iconLayerRepository: IconLayerRepository,
    private val iconMapper: IconMapper,
) {
    fun findAll(): Iterable<IconDTO> = iconMapper.toDTO(iconRepository.findAll())

    fun findByName(name: String): IconDTO? {
        val icon = iconRepository.findByName(name)
        return if (icon != null) iconMapper.toDTO(icon) else null
    }

    fun save(iconDTO: IconDTO): IconDTO {
        val icon = iconRepository.save(iconMapper.fromDTO(iconDTO))
        icon.iconStacks.forEach { iconStack ->
            iconStackRepository.save(iconStack).iconLayers.forEach { iconLayer ->
                iconLayerRepository.save(iconLayer)
            }
        }
        return iconMapper.toDTO(icon)
    }

    fun delete(iconDTO: IconDTO): IconDTO {
        if (iconDTO.id == null) {
            throw IllegalArgumentException("IconDTO has no id")
        }
        iconRepository.deleteById(iconDTO.id)
        return iconDTO
    }

    fun delete(iconDTOs: List<IconDTO>) = iconDTOs.map { delete(it) }

    fun render(icon: IconDTO): IconDTO = iconMapper.toDTO(iconMapper.fromDTO(icon))
}