package de.ilijaz.myapp.myapp.iconconfiguration.icon.pipe

import de.ilijaz.myapp.myapp.iconconfiguration.icon.pipe.pipes.BaseIconPipe
import de.ilijaz.myapp.myapp.iconconfiguration.icon.pipe.pipes.RotateIconPipe
import de.ilijaz.myapp.myapp.iconconfiguration.icon.pipe.pipes.ScaleIconPipe
import de.ilijaz.myapp.myapp.iconconfiguration.icon.vectorgraphic.VectorGraphicRepository
import org.springframework.stereotype.Component

@Component
class IconPipeFactory(private val vectorGraphicRepository: VectorGraphicRepository) {
    fun getPipeFactory(iconPipeEnum: IconPipeEnum): AbstractIconPipe = when (iconPipeEnum) {
        IconPipeEnum.Base -> BaseIconPipe(vectorGraphicRepository)
        IconPipeEnum.Rotate -> RotateIconPipe()
        IconPipeEnum.Scale -> ScaleIconPipe()
    }
}