package de.ilijaz.myapp.myapp.icon.vectorgraphic

import org.springframework.stereotype.Service

@Service
class VectorGraphicService(
    private val vectorGraphicRepository: VectorGraphicRepository,
) {
    fun getBySelector(vectorGraphicSelectorDTO: VectorGraphicSelectorDTO): VectorGraphic? {
        val vectorGraphics = vectorGraphicRepository.findByName(vectorGraphicSelectorDTO.name)
        return if (vectorGraphics.isEmpty()) {
            null
        } else vectorGraphics.find { it.type == vectorGraphicSelectorDTO.type } ?: vectorGraphics.first()
    }
}