package de.ilijaz.myapp.myapp.core

import org.w3c.dom.Document
import org.xml.sax.InputSource
import java.io.StringReader
import java.io.StringWriter
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.transform.OutputKeys
import javax.xml.transform.TransformerConfigurationException
import javax.xml.transform.TransformerException
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult


class SvgRoot(
    val document: Document,
) {
    val width: Int get() = document.documentElement.getAttribute("width").toInt()

    val height: Int get() = document.documentElement.getAttribute("height").toInt()

    val rootNode: SvgNode get() = SvgNode.nodeToSvgNode(this, document.documentElement)

    override fun toString(): String {
        val writer = StringWriter()
        try {
            val trans = TransformerFactory.newInstance().newTransformer()
            trans.setOutputProperty(OutputKeys.INDENT, "yes")
            trans.setOutputProperty(OutputKeys.VERSION, "1.0")
            if (document.documentElement !is Document) {
                trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes")
            }
            trans.transform(DOMSource(document.documentElement), StreamResult(writer))
        } catch (ex: TransformerConfigurationException) {
            throw IllegalStateException(ex)
        } catch (ex: TransformerException) {
            throw IllegalArgumentException(ex)
        }
        return writer.toString()
    }

    companion object {
        private val documentBuilder = DocumentBuilderFactory.newInstance()

        fun fromString(input: String): SvgRoot {
            try {
                val xml = documentBuilder.newDocumentBuilder().parse(InputSource(StringReader(input)))
                return SvgRoot(xml)
            } catch (e: Exception) {
                throw IllegalStateException("error while parsing xml", e)
            } catch (e: NumberFormatException) {
                throw IllegalStateException("error while parsing xml", e)
            }
        }

        fun default(): SvgRoot = fromString("<svg></svg>")
    }
}