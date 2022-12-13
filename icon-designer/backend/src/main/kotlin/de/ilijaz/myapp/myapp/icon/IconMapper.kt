package de.ilijaz.myapp.myapp.icon

import de.ilijaz.myapp.core.util.Preconditions
import de.ilijaz.myapp.myapp.vectorgraphic.VectorGraphicService
import org.springframework.stereotype.Component

@Component
class IconMapper(
    private val vectorGraphicService: VectorGraphicService,
) {
    fun fromDTO(dto: IconDTO): Icon = Icon(
        baseIcon = Preconditions.notNull(vectorGraphicService.findByName(dto.baseIcon)),
        additionalIcon = if (dto.additionalIcon == null) {
            null
        } else {
            Preconditions.notNull(vectorGraphicService.findByName(dto.additionalIcon))
        },
        backgroundIcon = if (dto.backgroundIcon == null) {
            null
        } else {
            Preconditions.notNull(vectorGraphicService.findByName(dto.backgroundIcon))
        },
        upperRightCornerIcon = if (dto.upperRightCornerIcon == null) {
            null
        } else {
            Preconditions.notNull(vectorGraphicService.findByName(dto.upperRightCornerIcon))
        },
        upperRightCornerIconBackground = if (dto.upperRightCornerIconBackground == null) {
            null
        } else {
            Preconditions.notNull(vectorGraphicService.findByName(dto.upperRightCornerIconBackground))
        },
        lowerRightCornerIcon = if (dto.lowerRightCornerIcon == null) {
            null
        } else {
            Preconditions.notNull(vectorGraphicService.findByName(dto.lowerRightCornerIcon))
        },
        lowerRightCornerIconBackground = if (dto.lowerRightCornerIconBackground == null) {
            null
        } else {
            Preconditions.notNull(vectorGraphicService.findByName(dto.lowerRightCornerIconBackground))
        },
        lowerLeftCornerIcon = if (dto.lowerLeftCornerIcon == null) {
            null
        } else {
            Preconditions.notNull(vectorGraphicService.findByName(dto.lowerLeftCornerIcon))
        },
        lowerLeftCornerIconBackground = if (dto.lowerLeftCornerIconBackground == null) {
            null
        } else {
            Preconditions.notNull(vectorGraphicService.findByName(dto.lowerLeftCornerIconBackground))
        },
        upperLeftCornerIcon = if (dto.upperLeftCornerIcon == null) {
            null
        } else {
            Preconditions.notNull(vectorGraphicService.findByName(dto.upperLeftCornerIcon))
        },
        upperLeftCornerIconBackground = if (dto.upperLeftCornerIconBackground == null) {
            null
        } else {
            Preconditions.notNull(
                vectorGraphicService.findByName(dto.upperLeftCornerIconBackground)
            )
        }
    )
}