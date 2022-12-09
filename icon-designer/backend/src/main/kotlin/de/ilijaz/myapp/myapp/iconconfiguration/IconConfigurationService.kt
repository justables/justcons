package de.ilijaz.myapp.myapp.iconconfiguration

import de.ilijaz.myapp.core.toXml
import de.ilijaz.myapp.myapp.iconconfiguration.dtos.IconConfigurationDTO
import org.springframework.stereotype.Service
import org.w3c.dom.Document
import org.w3c.dom.Node
import java.io.StringWriter
import javax.xml.transform.*
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult


@Service
class IconConfigurationService {
    fun configure(configuration: IconConfigurationDTO): String {
        var document = toXml("<svg></svg>")
        configuration.pipes.forEach { document = it.pipe.converter.apply(document, it.args) }
        return asString(document.documentElement) ?: "null"
    }

    fun getStringFromDocument(doc: Document?): String? {
        val domSource = DOMSource(doc)
        val writer = StringWriter()
        val result = StreamResult(writer)
        val tf = TransformerFactory.newInstance()
        val transformer: Transformer = tf.newTransformer()
        transformer.transform(domSource, result)
        return writer.toString()
    }

    private fun asString(node: Node): String? {
        val writer = StringWriter()
        try {
            val trans = TransformerFactory.newInstance().newTransformer()
            // @checkstyle MultipleStringLiterals (1 line)
            trans.setOutputProperty(OutputKeys.INDENT, "yes")
            trans.setOutputProperty(OutputKeys.VERSION, "1.0")
            if (node !is Document) {
                trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes")
            }
            trans.transform(DOMSource(node), StreamResult(writer))
        } catch (ex: TransformerConfigurationException) {
            throw IllegalStateException(ex)
        } catch (ex: TransformerException) {
            throw IllegalArgumentException(ex)
        }
        return writer.toString()
    }

}