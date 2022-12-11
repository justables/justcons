package de.ilijaz.myapp.myapp.icon.vectorgraphic

import org.apache.batik.transcoder.TranscoderInput
import org.apache.batik.transcoder.TranscoderOutput
import org.apache.batik.transcoder.image.PNGTranscoder
import org.springframework.stereotype.Service
import org.w3c.dom.Document
import org.xml.sax.InputSource
import java.io.ByteArrayOutputStream
import java.io.StringReader
import javax.xml.parsers.DocumentBuilderFactory


@Service
class VectorGraphicService(
    private val vectorGraphicRepository: VectorGraphicRepository,
    private val vectorGraphicMapper: VectorGraphicMapper,
) {
    fun findAll(): List<VectorGraphicDTO> = vectorGraphicRepository.findAll().map {
        val dto = vectorGraphicMapper.toDTO(it)
        dto.image = svgToPng(it.computeSvg().replace("currentColor", "black"))
        dto
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

    private fun svgToPngOld(svg: String): ByteArray {
        ByteArrayOutputStream().use {
            PNGTranscoder().transcode(TranscoderInput(stringToXml(svg)), TranscoderOutput(it))
            return it.toByteArray()
        }
    }

    private fun svgToPng(svg: String): ByteArray {
        val transcoderInput = TranscoderInput(StringReader(svg))
        val output = ByteArrayOutputStream()
        val transcoderOutput = TranscoderOutput(output)
        output.use {
            val pngTranscoder = PNGTranscoder()
            pngTranscoder.addTranscodingHint(PNGTranscoder.KEY_WIDTH, 128f)
            pngTranscoder.addTranscodingHint(PNGTranscoder.KEY_HEIGHT, 128f)
            pngTranscoder.transcode(transcoderInput, transcoderOutput)
            return output.toByteArray()
        }

    }

    private fun stringToXml(svg: String): Document {
        val documentBuilder = DocumentBuilderFactory.newInstance()
            .newDocumentBuilder()
        return documentBuilder.parse(InputSource(StringReader(svg)))
    }
}