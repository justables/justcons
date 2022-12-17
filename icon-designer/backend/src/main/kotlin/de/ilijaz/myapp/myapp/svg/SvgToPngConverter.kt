package de.ilijaz.myapp.myapp.svg

import org.apache.batik.transcoder.TranscoderInput
import org.apache.batik.transcoder.TranscoderOutput
import org.apache.batik.transcoder.image.PNGTranscoder
import java.io.ByteArrayOutputStream
import java.io.StringReader

class SvgToPngConverter {
    companion object {
        fun svgToPng(svg: String): ByteArray {
//            val coloredSvg = svg.replace(Regex("fill=\"(white|black|currentColor)\""), "fill=\"black\"")
//                .replace(Regex("color=\"(white|black|currentColor)\""), "color=\"black\"")
//                .replace(Regex("stroke=\"(white|black|currentColor)\""), "stroke=\"black\"")
//                .replace(Regex("#ffffff"), "#000000")
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
    }
}