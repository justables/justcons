package de.ilijaz.myapp.myapp.iconconfiguration.icon.pipe.pipes

import de.ilijaz.myapp.core.toXml
import de.ilijaz.myapp.myapp.iconconfiguration.icon.pipe.AbstractIconPipe
import org.w3c.dom.Document

class BaseIconPipe : AbstractIconPipe() {
    override fun apply(document: Document, args: Any): Document = toXml(args as String);
}