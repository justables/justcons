package de.ilijaz.myapp.myapp.iconconfiguration

import de.ilijaz.myapp.myapp.iconconfiguration.dtos.IconConfigurationDTO
import org.springframework.web.bind.annotation.*


@RestController
@CrossOrigin(origins = ["*"])
class IconConfigurationController(
    val iconConfigurationService: IconConfigurationService,
) {
    @GetMapping("/icon")
    fun getAll(@RequestBody configuration: IconConfigurationDTO): String =
        iconConfigurationService.configure(configuration)

}
