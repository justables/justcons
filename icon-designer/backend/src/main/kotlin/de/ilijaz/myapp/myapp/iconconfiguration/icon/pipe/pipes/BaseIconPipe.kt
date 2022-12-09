package de.ilijaz.myapp.myapp.iconconfiguration.icon.pipe.pipes

import de.ilijaz.myapp.core.util.Preconditions
import de.ilijaz.myapp.myapp.core.SvgRoot
import de.ilijaz.myapp.myapp.iconconfiguration.icon.pipe.AbstractIconPipe
import de.ilijaz.myapp.myapp.iconconfiguration.icon.vectorgraphic.VectorGraphicRepository
import org.springframework.stereotype.Component

@Component
class BaseIconPipe(private val vectorGraphicRepository: VectorGraphicRepository) : AbstractIconPipe() {
    override fun apply(root: SvgRoot, args: Any): SvgRoot {
        Preconditions.type<String>(args)
        val vectorGraphic = Preconditions.notNull(vectorGraphicRepository.findByName(args as String))
        return SvgRoot.fromString(vectorGraphic.vectorGraphic)
    }
}