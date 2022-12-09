package de.ilijaz.myapp.myapp.iconconfiguration.icon.pipe

import de.ilijaz.myapp.myapp.core.SvgRoot

abstract class AbstractIconPipe {
    abstract fun apply(root: SvgRoot, args: Any): SvgRoot
}