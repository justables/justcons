package de.ilijaz.myapp.myapp.icon.vectorgraphic

import org.springframework.stereotype.Service

@Service
class VectorGraphicService(
    private val vectorGraphicRepository: VectorGraphicRepository,
) {
    /**
     * tries to find the vector graphic with the inputted name and matching theme. If the theme doesn't match, it will
     * return the vector graphic with the other possible theme or null
     */
    fun getBySelector(vectorGraphicSelectorDTO: VectorGraphicSelectorDTO): VectorGraphic? {
        val vectorGraphics = vectorGraphicRepository.findByName(vectorGraphicSelectorDTO.name)
        return if (vectorGraphics.isEmpty()) {
            null
        } else vectorGraphics.find { it.type == vectorGraphicSelectorDTO.type } ?: vectorGraphics.first()
    }
}