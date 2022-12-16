package de.ilijaz.myapp.myapp.icon

import de.ilijaz.myapp.myapp.icon.dto.IconDTO
import de.ilijaz.myapp.myapp.icon.dto.IconStackDTO
import org.springframework.web.bind.annotation.*


@RestController
@CrossOrigin(origins = ["*"])
class IconController(
    private val iconService: IconService,
) {
    @GetMapping("/icon")
    fun getAll(): Iterable<IconDTO> = iconService.findAll()

    @PostMapping("/icon/render-stack")
    fun renderStack(@RequestBody iconStack: IconStackDTO): ByteArray = iconService.renderStack(iconStack)

}
