package de.ilijaz.myapp.myapp.icon.vectorgraphic

import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

private const val delimiter = ":"

@Entity
@Table(name = "vector_graphics")
class VectorGraphic(
    @Id val id: UUID,
    val name: String,
    /**
     * list of svg paths separated using a ';'
     */
    @Column(columnDefinition = "TEXT") val paths: String,
    val type: VectorGraphicType,
    /**
     * composed of four parts: "<x-translation>:<y-translation>:<scale>:<rotation>"
     */
    val transformation: String,
) {

    companion object {
        fun createTransformation(xTranslation: Float, yTranslation: Float, scale: Float, rotation: Float) =
            "$xTranslation$delimiter$yTranslation$delimiter$scale$delimiter$rotation"
    }

    val xTranslation: Float get() = parseFloat(transformation.split(delimiter).getOrElse(0) { "0" })
    val yTranslation: Float get() = parseFloat(transformation.split(delimiter).getOrElse(1) { "0" })
    val scale: Float get() = parseFloat(transformation.split(delimiter).getOrElse(2) { "1" })
    val rotation: Float get() = parseFloat(transformation.split(delimiter).getOrElse(3) { "0" })

    private fun parseFloat(value: String): Float = if (value.isBlank()) 0f else value.toFloat()
}