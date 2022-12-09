package de.ilijaz.myapp.core;

import org.w3c.dom.Document
import org.xml.sax.InputSource
import java.io.StringReader
import javax.xml.parsers.DocumentBuilderFactory


class XmlConverter

fun toXml(input: String): Document = try {
    DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(InputSource(StringReader(input)))
} catch (e: Exception) {
    throw IllegalStateException("error while parsing xml", e)
}

fun fromXml(document: Document): String = document.toString();