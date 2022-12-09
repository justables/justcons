package de.ilijaz.myapp.myapp.iconconfiguration.icon.pipe

import org.w3c.dom.Document

abstract class AbstractIconPipe {
    abstract fun apply(document: Document, args: Any): Document
}