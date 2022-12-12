package de.ilijaz.myapp.myapp.vectorgraphic

import de.ilijaz.myapp.core.util.Preconditions
import de.ilijaz.myapp.myapp.svg.SvgToPngConverter
import org.springframework.stereotype.Service


@Service
class VectorGraphicService(
    private val vectorGraphicRepository: VectorGraphicRepository,
    private val vectorGraphicMapper: VectorGraphicMapper,
) {
    fun findAll(): List<VectorGraphicDTO> = vectorGraphicRepository.findAll().map {
        val dto = vectorGraphicMapper.toDTO(it)
        dto.image = SvgToPngConverter.svgToPng(it.computeSvg())
        dto
    }

    fun save(vectorGraphics: List<VectorGraphicDTO>): Iterable<VectorGraphicDTO> {
        vectorGraphics.forEach {
            vectorGraphicRepository.save(
                vectorGraphicMapper.fromDTO(it)
            )
        }
        return vectorGraphics
    }

    fun delete(vectorGraphics: List<VectorGraphicDTO>): Iterable<VectorGraphicDTO> {
        vectorGraphics.forEach {
            Preconditions.notNull(it.id)
            vectorGraphicRepository.deleteById(it.id!!)
        }
        return vectorGraphics
    }

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