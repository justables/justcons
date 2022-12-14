package de.ilijaz.myapp.myapp.svg

import de.ilijaz.myapp.core.util.XmlUtil
import org.springframework.stereotype.Service
import org.w3c.dom.Element
import org.w3c.dom.Node
import java.util.*
import java.util.stream.IntStream
import kotlin.io.path.Path
import kotlin.io.path.writeText

@Service
class SvgToPathConverterService {

    fun convert(svg: String): SvgToPathConverterResultDTO = SvgToPathConverterResultDTO(
        convertSvgToPath(svg), getDimensions(svg), SvgToPngConverter.svgToPng(convertSvgToSvgPath(svg))
    )

    fun convertSvgToPath(svg: String): String {
        val document = XmlUtil.stringToXml(convertSvgToSvgPath(svg))
        val nodeList = document.documentElement.getElementsByTagName("path")
        val paths = IntStream.range(0, nodeList.length).mapToObj(nodeList::item).toList()
        return paths
            .filter { isVisiblePath(it) }
            .map {
                if (it.attributes.getNamedItem("id") != null) it.attributes.removeNamedItem("id")
                if (it.attributes.getNamedItem("mask") != null) it.attributes.removeNamedItem("mask")
                if (it.attributes.getNamedItem("clip-rect") != null) it.attributes.removeNamedItem("clip-rect")
                it
            }
            .joinToString { XmlUtil.nodeToString(it) }
    }

    fun getDimensions(svg: String): Float =
        XmlUtil.stringToXml(svg).documentElement.attributes.getNamedItem("width").textContent.toFloat()

    fun convertSvgToSvgPath(svg: String): String {
        val iconPath = UUID.randomUUID()
        Path("/tmp/$iconPath.svg").writeText(svg)
        val pb = ProcessBuilder(
            "inkscape",
            "--actions=select-all;object-to-path;object-stroke-to-path;",
            "--export-filename=-",
            "/tmp/$iconPath.svg"
        )
        val process = pb.start()
        return String(process.inputStream.readAllBytes())
    }

    /**
     * a node is considered a visible node when it is not used inside a mask or clip-rect.
     */
    private fun isVisiblePath(node: Node): Boolean {
        if (node.parentNode is Element) {
            when ((node.parentNode as Element).tagName) {
                "svg" -> return true
                "mask" -> return false
                "clip-rect" -> return false
            }
        }
        return isVisiblePath(node.parentNode)
    }
}