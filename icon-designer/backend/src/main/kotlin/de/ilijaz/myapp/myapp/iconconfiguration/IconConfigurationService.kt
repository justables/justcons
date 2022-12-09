package de.ilijaz.myapp.myapp.iconconfiguration

import de.ilijaz.myapp.myapp.core.SvgRoot
import de.ilijaz.myapp.myapp.iconconfiguration.dtos.IconConfigurationDTO
import de.ilijaz.myapp.myapp.iconconfiguration.icon.pipe.IconPipeFactory
import org.springframework.stereotype.Service


@Service
class IconConfigurationService(private val iconPipeFactory: IconPipeFactory) {
    fun configure(configuration: IconConfigurationDTO): String {
        var document = SvgRoot.default()
        configuration.pipes.forEach { document = iconPipeFactory.getPipeFactory(it.pipe).apply(document, it.args) }
        return document.toString()
    }

}