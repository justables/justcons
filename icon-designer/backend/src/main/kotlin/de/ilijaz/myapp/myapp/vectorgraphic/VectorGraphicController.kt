package de.ilijaz.myapp.myapp.vectorgraphic

import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin(origins = ["*"])
class VectorGraphicController(
    private val vectorGraphicService: VectorGraphicService
) {

    @GetMapping("/vector-graphic")
    fun getAll(): Iterable<VectorGraphicDTO> = vectorGraphicService.findAll()

    @PostMapping("/vector-graphic")
    fun save(@RequestBody vectorGraphics: List<VectorGraphicDTO>): Iterable<VectorGraphicDTO> =
        vectorGraphicService.save(vectorGraphics)

    @DeleteMapping("/vector-graphic")
    fun delete(@RequestBody vectorGraphics: List<VectorGraphicDTO>): Iterable<VectorGraphicDTO> =
        vectorGraphicService.delete(vectorGraphics)
}