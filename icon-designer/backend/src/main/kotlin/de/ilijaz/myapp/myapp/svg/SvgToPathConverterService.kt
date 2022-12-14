package de.ilijaz.myapp.myapp.svg

import org.springframework.stereotype.Service
import org.w3c.dom.Document
import org.xml.sax.InputSource
import java.io.StringReader
import java.util.*
import java.util.stream.IntStream
import javax.xml.parsers.DocumentBuilderFactory
import kotlin.io.path.Path
import kotlin.io.path.writeText

@Service
class SvgToPathConverterService {

    fun convert(svg: String): SvgToPathConverterResultDTO = SvgToPathConverterResultDTO(
        convertSvgToPath(svg),
        getDimensions(svg),
        SvgToPngConverter.svgToPng(convertSvgToSvgPath(svg))
    )

    fun convertSvgToPath(svg: String): String {
        val document = stringToXml(convertSvgToSvgPath(svg))
        val nodeList = document.documentElement.getElementsByTagName("path")
        val paths = IntStream.range(0, nodeList.length).mapToObj(nodeList::item).toList()
        return paths.map { it.attributes.getNamedItem("d").textContent }.toList().joinToString(";")
    }

    fun getDimensions(svg: String): Float =
        stringToXml(svg).documentElement.attributes.getNamedItem("width").textContent.toFloat()

    fun convertSvgToSvgPath(svg: String): String {
        val iconPath = UUID.randomUUID()
        Path("/tmp/$iconPath.svg").writeText(svg)
        val pb =
            ProcessBuilder(
                "inkscape",
                "--actions=select-all;object-to-path;object-stroke-to-path;",
                "--export-filename=-",
                "/tmp/$iconPath.svg"
            )
        val process = pb.start()
        return String(process.inputStream.readAllBytes())
    }

    private fun stringToXml(svg: String): Document {
        val documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder()
        return documentBuilder.parse(InputSource(StringReader(svg)))
    }
}