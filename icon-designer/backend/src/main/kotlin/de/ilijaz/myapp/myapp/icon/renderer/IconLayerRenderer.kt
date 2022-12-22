package de.ilijaz.myapp.myapp.icon.renderer

import de.ilijaz.myapp.myapp.icon.domain.IconLayer
import java.util.*
import kotlin.math.sin
import kotlin.math.sqrt

class IconLayerRenderer(
    private val iconLayer: IconLayer,
    private val dimensions: Int,
    private val nextLayers: List<IconLayer>,
    private val previousLayer: IconLayerRenderer? = null,
) {
    private val id = UUID.randomUUID().toString()

    private var mask = false

    fun render(): IconGroupsAndDefs {
        if (previousLayer?.iconLayer?.vectorGraphic?.mask == true) {
            mask = !mask
        }
        val nextLayer = renderNextLayer()
        val transform = calculateTransform()
        return IconGroupsAndDefs(renderMasks(nextLayer, transform), renderGroups(nextLayer, transform))
    }

    private fun renderGroups(nextLayer: IconGroupsAndDefs?, transform: String): ArrayList<String> {
        val lines = ArrayList<String>()
        val id = if (iconLayer.vectorGraphic?.mask == true && !isLastLayer) {
            " mask=\"url(#$id)\""
        } else {
            ""
        }
        lines.add("<g$transform$id>")
        if (!mask) {
            renderPaths?.let { lines.addAll(IconRendererUtil.indentBy(1, it)) }
        }
        nextLayer?.let { lines.addAll(IconRendererUtil.indentBy(1, it.groups)) }
        lines.add("</g>")
        return lines
    }

    private fun renderMasks(nextLayer: IconGroupsAndDefs?, transform: String): List<String> {
        val lines = ArrayList<String>()
        if (mask) {
            lines.add("<mask id=\"${previousLayer?.id}\">")
            lines.add(IconRendererUtil.indentBy(1, "<rect width=\"100%\" height=\"100%\" fill=\"white\" />"))
            lines.add(IconRendererUtil.indentBy(1, "<g$transform>"))
            renderPaths?.let { lines.addAll(IconRendererUtil.indentBy(2, removeColor(it))) }
            lines.add(IconRendererUtil.indentBy(1, "</g>"))
            lines.add("</mask>")
        }
        nextLayer?.let { lines.addAll(it.defs) }
        return lines
    }

    private val renderPaths: List<String>? get() = iconLayer.vectorGraphic?.paths?.let { listOf(it) }

    private val isLastLayer: Boolean get() = nextLayers.isEmpty()

    private fun renderNextLayer(): IconGroupsAndDefs? = nextLayers.firstOrNull()?.let { layer ->
        IconLayerRenderer(
            layer,
            dimensions,
            nextLayers.toMutableList().let { it.remove(it.first()); it },
            this
        ).render()
    }

    private fun calculateTransform(): String {
        val vector = previousLayer?.iconLayer?.vectorGraphic ?: return ""
        val result = ArrayList<String>()
        result.add("translate(${vector.translationX} ${vector.translationY})")
        result.add(calculateScaleTransform(vector.scale))
        result.add("scale(${vector.scale})")
        result.add("rotate(${vector.rotation})")
        result.add(calculateRotationTransform(vector.rotation.toDouble()))
        return " transform=\"${result.joinToString(" ")}\""
    }

    private fun calculateRotationTransform(rotation: Double): String {
        val offset = sqrt(2.0) * dimensions * 3 / 8
        var translationX =
            sin(Math.toRadians(rotation - 45)) * dimensions * 3 / 4 + offset
        var translationY =
            sin(Math.toRadians(rotation - 135)) * dimensions * 3 / 4 + offset
        return "translate($translationX $translationY)"
    }

    private fun calculateScaleTransform(scale: Float): String {
        var scaleOffset = (dimensions * (1 - scale)) / 2
        return "translate($scaleOffset $scaleOffset)"
    }

    private fun removeColor(svg: String): String =
        svg.replace(Regex("(fill|stroke)(=\\\"#?[A-z0-9]+\\\"|:#?[A-z0-9]+)"), "")

    private fun removeColor(svg: List<String>): List<String> = svg.map { removeColor(it) }
}