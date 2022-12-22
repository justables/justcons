package de.ilijaz.myapp.myapp.svg

import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
@CrossOrigin(origins = ["*"])
class SvgToPathConverterController(
    private val svgToPathConverterService: SvgToPathConverterService,
) {
    @PostMapping("/svg-to-path")
    fun svgToPath(@RequestBody svgConverterDTO: SvgToPathConverterRequestDTO): SvgToPathConverterResultDTO =
        svgToPathConverterService.convert(svgConverterDTO)
}