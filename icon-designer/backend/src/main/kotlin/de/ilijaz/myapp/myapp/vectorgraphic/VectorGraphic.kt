package de.ilijaz.myapp.myapp.vectorgraphic

import de.ilijaz.myapp.myapp.svg.IconToSvgConverter
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table


@Entity
@Table(name = "vector_graphics")
class VectorGraphic(
    @Id val id: UUID,
    @Column(unique = true) val name: String,
    /**
     * list of svg paths separated using a ';'
     */
    @Column(columnDefinition = "TEXT") val paths: String,
    val translationX: Float = 0f,
    val translationY: Float = 0f,
    val scale: Float = 1f,
    val rotation: Float = 0f,
    val mask: Boolean = false,
) {
    fun computeSvgPaths(): String = paths.split(";").joinToString("\n") { "<path d=\"$it\" color=\"currentColor\" />" }

    fun computeSvg(): String =
        "<svg xmlns:xlink=\"http://www.w3.org/1999/xlink\" xmlns=\"http://www.w3.org/2000/svg\" version=\"1.0\"" + " width=\"32\" height=\"32\" viewBox=\"0 0 24 24\">${
            IconToSvgConverter.indentBy(computeSvgPaths(), 1)
        }\n</svg>"
}