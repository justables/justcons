package de.ilijaz.myapp.myapp.icon

import org.springframework.stereotype.Service

@Service
class IconService(
    private val iconMapper: IconMapper,
) {
    fun generate(iconDTO: IconDTO): String {
        val icon = iconMapper.fromDTO(iconDTO)
        return icon.render()
    }

}