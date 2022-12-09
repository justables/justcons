package de.ilijaz.myapp.myapp.iconconfiguration.icon.pipe.pipes

import de.ilijaz.myapp.myapp.iconconfiguration.icon.pipe.AbstractIconPipe
import org.w3c.dom.Attr
import org.w3c.dom.Document

class RotateIconPipe : AbstractIconPipe() {
    override fun apply(document: Document, args: Any): Document {
        val firstChild = document.documentElement.firstChild
        document.documentElement.removeChild(firstChild)
        val rotateNode = document.createElement("g")
        val transformAttr: Attr = document.createAttribute("transform")
        transformAttr.nodeValue = "translate(0 0) rotate(0)"
        rotateNode.attributes.setNamedItem(transformAttr)
        rotateNode.appendChild(firstChild)
        document.documentElement.appendChild(rotateNode)
        return document
    }
}