package de.ilijaz.myapp.myapp.icon

import de.ilijaz.myapp.myapp.icon.dto.IconDTO
import org.springframework.web.bind.annotation.*


@RestController
@CrossOrigin(origins = ["*"])
class IconController(
    private val iconService: IconService,
) {
    @GetMapping("/icon")
    fun getAll(): Iterable<IconDTO> = iconService.findAll()

    @PostMapping("/icon/render")
    fun render(@RequestBody icon: IconDTO): IconDTO = iconService.render(icon)

}
