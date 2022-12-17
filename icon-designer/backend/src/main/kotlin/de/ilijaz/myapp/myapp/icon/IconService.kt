package de.ilijaz.myapp.myapp.icon

import de.ilijaz.myapp.myapp.icon.dto.IconDTO
import org.springframework.stereotype.Service

@Service
class IconService(
    private val iconRepository: IconRepository,
    private val iconMapper: IconMapper,
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

    fun render(icon: IconDTO): IconDTO = iconMapper.toDTO(iconMapper.fromDTO(icon))
}