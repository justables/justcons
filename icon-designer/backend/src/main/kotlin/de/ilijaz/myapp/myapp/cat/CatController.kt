package de.ilijaz.myapp.myapp.cat

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*


@RestController
@CrossOrigin(origins = ["*"])
class CatController(
    private val catService: CatService,
) {
    @GetMapping("/cats")
    fun getAll(): Iterable<CatDTO> = catService.findAll()

    @GetMapping("/cat/{id}")
    fun get(@PathVariable id: Int) = catService.find(id)

    @PostMapping("/cat")
    @ResponseStatus(HttpStatus.CREATED)
    fun post(@RequestBody cat: CatDTO) = catService.save(cat)

    @DeleteMapping("/cat/{id}")
    fun delete(@PathVariable id: Int) = catService.delete(id)
}
