package de.ilijaz.myapp.myapp.icon

import org.springframework.web.bind.annotation.*


@RestController
@CrossOrigin(origins = ["*"])
class IconController(
    val iconService: IconService,
) {
    @PostMapping("/icon")
    fun getAll(@RequestBody icon: IconDTO): String =
        iconService.generate(icon)

}
