package de.ilijaz.skaagen.service.dtos

import de.ilijaz.skaagen.*
import java.io.File
import kotlin.reflect.KClass
import kotlin.reflect.full.isSubclassOf
import kotlin.reflect.full.memberProperties

class DTOGenerator(private val dtoClass: KClass<out Any>) {

    private val path = getPackagePathOfKClass(dtoClass)
    private val dependencyCollection = DependencyCollection(path)

    fun writeFile() {
        val fileName = toKebabCase(dtoClass.simpleName!!) + ".ts"
        val path =
            listOf(
                targetPath,
                dtoClass.java.`package`.name.replace("$rootPackage.", "").replace(".", "/")
            ).joinToString("/")
        val fullPath = listOf(path, fileName).joinToString("/")
        val code = toCode()
        File(path).mkdirs()
        File(fullPath).writeText(code)
        dtoCollection.remove(dtoClass)
        generatedDTOs.add(dtoClass)
    }

    private fun toCode(): String {
        if (dtoClass.isSubclassOf(Enum::class)) {
            return toEnumCode()
        }
        val properties = generateProperties()
        val setters = generateSetters()
        val dependencies = dependencyCollection.toCode()

        return """/** generated code */$dependencies
export interface ${dtoClass.simpleName} {
$properties
}

export class ${dtoClass.simpleName!!.replace("DTO", "")} {
$properties

  constructor(data: ${dtoClass.simpleName}) {
$setters
  }
}
        """.trimIndent()
    }

    private fun toEnumCode(): String {
        return """/** generated code */
export type ${dtoClass.simpleName} = ${enumValues().joinToString(" | ")};
export const all${dtoClass.simpleName}: ${dtoClass.simpleName}[] = [${enumValues().joinToString(", ")}];
        """.trimIndent()
    }

    private fun enumValues() = ((dtoClass as KClass<Enum<*>>).java.enumConstants.asList().map { "'${it.name}'" })

    private fun generateProperties(): String {
        return dtoClass.memberProperties.joinToString("\n") { member ->
            "  ${member.name}: ${kotlinTypeToNullableTsType(member.returnType, dependencyCollection)};"
        }
    }

    private fun generateSetters(): String {
        return dtoClass.memberProperties.joinToString("\n") { member ->
            "    this.${member.name} = data.${member.name};"
        }
    }
}

val dtoCollection: MutableSet<KClass<out Any>> = mutableSetOf()
private val generatedDTOs: MutableSet<KClass<out Any>> = mutableSetOf()

fun generateDTOs() {
    generatedDTOs.forEach { dtoCollection.remove(it) }
    dtoCollection.toMutableList().forEach { DTOGenerator(it).writeFile() }
}