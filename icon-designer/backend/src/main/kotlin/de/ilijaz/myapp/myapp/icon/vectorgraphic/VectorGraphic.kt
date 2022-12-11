package de.ilijaz.myapp.myapp.icon.vectorgraphic

import de.ilijaz.myapp.myapp.icon.svg.SvgBuilder
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

    fun computeSvgPaths(): String = paths.split(";").joinToString("\n") { "<path d=\"$it\" color=\"currentColor\" />" }

    fun computeSvg(): String =
        "<svg xmlns:xlink=\"http://www.w3.org/1999/xlink\" xmlns=\"http://www.w3.org/2000/svg\" version=\"1.0\"" +
            " width=\"32\" height=\"32\" viewBox=\"0 0 24 24\">${
                SvgBuilder.indentBy(computeSvgPaths(), 1)
            }\n</svg>"

    private fun parseFloat(value: String): Float = if (value.isBlank()) 0f else value.toFloat()
}