package de.ilijaz.skaagen

import de.ilijaz.myapp.myapp.cat.CatController
import de.ilijaz.myapp.myapp.icon.vectorgraphic.VectorGraphicController
import de.ilijaz.skaagen.service.ServiceGenerator
import de.ilijaz.skaagen.service.dtos.dtoCollection
import de.ilijaz.skaagen.service.dtos.generateDTOs


class Generator

val controller = listOf(
    CatController::class,
)

fun main(args: Array<String>) {
    args.forEach {
        if (it.startsWith("--source=")) {
            sourcePath = it.substring(9)
        }
        if (it.startsWith("--target=")) {
            targetPath = it.substring(9)
        }
        if (it.startsWith("--package=")) {
            rootPackage = it.substring(10)
        }
    }
    ServiceGenerator(VectorGraphicController::class).writeFile()
    while (dtoCollection.isNotEmpty()) {
        generateDTOs()
    }
}
