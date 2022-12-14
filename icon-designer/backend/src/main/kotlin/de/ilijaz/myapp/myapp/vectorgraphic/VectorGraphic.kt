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
    val dimensions: Float,
    val translationX: Float = 0f,
    val translationY: Float = 0f,
    val scale: Float = 1f,
    val rotation: Float = 0f,
    val mask: Boolean = false,
) {
    fun computeSvg(): String =
        "<svg xmlns:xlink=\"http://www.w3.org/1999/xlink\" xmlns=\"http://www.w3.org/2000/svg\" version=\"1.0\" width=\"$dimensions\" height=\"$dimensions\" viewBox=\"0 0 $dimensions $dimensions\">${
            IconToSvgConverter.indentBy(paths, 1)
        }\n</svg>"
}