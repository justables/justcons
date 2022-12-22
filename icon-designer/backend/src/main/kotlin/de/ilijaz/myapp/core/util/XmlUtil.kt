package de.ilijaz.myapp.core.util

import org.w3c.dom.Document
import org.w3c.dom.Node
import org.w3c.dom.ls.DOMImplementationLS
import org.xml.sax.InputSource
import java.io.StringReader
import java.util.stream.IntStream
import java.util.stream.Stream
import javax.xml.parsers.DocumentBuilderFactory


class XmlUtil {
    companion object {
        fun stringToXml(svg: String): Document {
            val documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder()
            return documentBuilder.parse(InputSource(StringReader(svg)))
        }

        fun nodeToString(node: Node): String {
            val serializer =
                (node.ownerDocument.implementation.getFeature("LS", "3.0") as DOMImplementationLS).createLSSerializer()
            serializer.domConfig.setParameter(
                "xml-declaration",
                false
            )
            return serializer.writeToString(node)
        }

        fun children(node: Node): Stream<Node> =
            IntStream.range(0, node.childNodes.length).mapToObj(node.childNodes::item)
    }
}