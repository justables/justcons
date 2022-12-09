package de.ilijaz.skaagen

import de.ilijaz.skaagen.service.dtos.dtoCollection
import kotlin.reflect.KClass
import kotlin.reflect.KType

private const val DELETE_INDICATOR_STRING = "*delete*"

class DependencyCollection(private val targetPath: List<String>) {
    private val staticDependencies: MutableMap<String, MutableSet<String>> = HashMap()
    private val dynamicDependencies: MutableMap<String, MutableSet<String>> = HashMap()
    private val classDependencies: MutableSet<KType> = HashSet()

    fun add(dependency: String, source: String) {
        if (!staticDependencies.containsKey(source)) {
            staticDependencies[source] = HashSet()
        }
        staticDependencies[source]!! += dependency
    }

    fun add(dependency: String, sourcePath: List<String>) {
        val source = sourcePath.joinToString("/")
        if (!dynamicDependencies.containsKey(source)) {
            dynamicDependencies[source] = HashSet()
        }
        dynamicDependencies[source]!! += dependency
    }

    fun add(dependency: KType) {
        val nestedType = getNestedType(dependency)
        if (classDependencies.none { removeNullableFromType(it) == removeNullableFromType(nestedType) }) {
            classDependencies += nestedType
            dtoCollection.add((nestedType.classifier as KClass<out Any>))
        }
    }

    fun toCode() = "${staticDependenciesToCode()}\n${dynamicDependenciesToCode()}\n${classDependenciesToCode()}"
        .replace("./..", "..")

    private fun staticDependenciesToCode() = staticDependencies
        .map { "import { ${it.value.joinToString(", ")} } from '${it.key}';" }
        .joinToString("\n")

    private fun dynamicDependenciesToCode() = dynamicDependencies
        .map { dynamicDependencyToCode(it.value, it.key) }
        .joinToString("\n")

    private fun dynamicDependencyToCode(imports: Set<String>, path: String): String {
        val targetPath = this.targetPath.toMutableList()
        var packagePath = path.split("/").toMutableList()
        val packagePathString = calculateRelativePath(targetPath, packagePath)
        return "import { ${imports.joinToString(", ")} } from './$packagePathString';"
    }

    private fun classDependenciesToCode() = classDependencies.joinToString("\n") { classDependencyToCode(it) }

    private fun classDependencyToCode(typeDependency: KType): String {
        val targetPath = this.targetPath.toMutableList()
        var packagePath = getPackagePathOfKClass(typeDependency)
        val packagePathString = calculateRelativePath(targetPath, packagePath)

        val className = removeNullableFromType(typeDependency).split(".").last()
        val fileName = toKebabCase(className).replace("-dto", ".dto")
        return "import { $className } from './$packagePathString/$fileName';"
    }

    private fun calculateRelativePath(targetPath: MutableList<String>, packagePath: MutableList<String>): String {
        for ((i, path) in targetPath.withIndex()) {
            if (i < packagePath.size && packagePath[i] == path) {
                packagePath[i] = DELETE_INDICATOR_STRING
                targetPath[i] = DELETE_INDICATOR_STRING
            } else {
                break
            }
        }
        var newPackagePath = (
                targetPath
                    .filter { it != DELETE_INDICATOR_STRING }
                    .map { ".." }
                    .toMutableList()
                        + packagePath
                    .filter { it != DELETE_INDICATOR_STRING }
                    .toMutableList()
                ).toMutableList()
        return newPackagePath.joinToString("/")
    }

    private fun removeNullableFromType(typeDependency: KType) = typeDependency.toString().replace("?", "")
}
