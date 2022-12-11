package de.ilijaz.myapp.myapp.vectorgraphic

import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@CrossOrigin(origins = ["*"])
class VectorGraphicController(
    private val vectorGraphicService: VectorGraphicService
) {

    @GetMapping("/vector-graphics")
    fun getAll(): Iterable<VectorGraphicDTO> = vectorGraphicService.findAll()
}