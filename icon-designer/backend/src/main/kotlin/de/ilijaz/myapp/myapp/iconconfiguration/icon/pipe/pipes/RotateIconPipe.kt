package de.ilijaz.myapp.myapp.iconconfiguration.icon.pipe.pipes

import de.ilijaz.myapp.myapp.core.SvgNode
import de.ilijaz.myapp.myapp.core.SvgRoot
import de.ilijaz.myapp.myapp.iconconfiguration.icon.pipe.AbstractIconPipe

class RotateIconPipe : AbstractIconPipe() {
    override fun apply(root: SvgRoot, args: Any): SvgRoot {
        root.rootNode.wrapChildren(
            SvgNode.createElement(root, "g"),
            mapOf(
                "transform" to "rotate($args)",
                "transform-origin" to "center"
            )
        )
        return root
    }
}