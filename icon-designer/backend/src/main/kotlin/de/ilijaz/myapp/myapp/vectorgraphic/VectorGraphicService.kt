package de.ilijaz.myapp.myapp.vectorgraphic

import de.ilijaz.myapp.core.util.Preconditions
import org.springframework.stereotype.Service


@Service
class VectorGraphicService(
    private val vectorGraphicRepository: VectorGraphicRepository,
    private val vectorGraphicMapper: VectorGraphicMapper,
) {
    fun findAll(): List<VectorGraphicDTO> = vectorGraphicRepository.findAll().map {
        val dto = vectorGraphicMapper.toDTO(it)
        dto
    }

    fun findByName(vectorGraphicName: String): VectorGraphic? = vectorGraphicRepository.findByName(vectorGraphicName)

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

}