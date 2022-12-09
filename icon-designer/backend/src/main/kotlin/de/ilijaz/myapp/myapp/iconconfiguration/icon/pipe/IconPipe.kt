package de.ilijaz.myapp.myapp.iconconfiguration.icon.pipe

import de.ilijaz.myapp.myapp.iconconfiguration.icon.pipe.pipes.BaseIconPipe
import de.ilijaz.myapp.myapp.iconconfiguration.icon.pipe.pipes.RotateIconPipe

enum class IconPipe(
    val pipeName: String,
    val converter: AbstractIconPipe,
) {
    BaseIcon("base", BaseIconPipe()),
    RotateIcon("rotate", RotateIconPipe()),
}