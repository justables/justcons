package de.ilijaz.skaagen

import kotlin.reflect.KType

fun kotlinTypeToTsType(kotlinType: KType, dependencyCollection: DependencyCollection): String {
    var tsType = kotlinType.toString()
        .split(" ")
        .first()
        .split("<")
        .first()
        .split(".")
        .last()
        .replace("!", "")
        .replace("?", "")
    if (tsType == "Optional") {
        tsType = kotlinTypeToNullableTsType(kotlinType.arguments[0].type!!, dependencyCollection)
    }
    if (tsType == "Iterable" || tsType == "List") {
        tsType = kotlinTypeToNullableTsType(kotlinType.arguments[0].type!!, dependencyCollection) + "[]"
    }

    tsType = tsType
        .replace("*", "")
        .replace(" ", "")
        .replace("/", "")
        .replace(">", "")

    tsType = when (tsType) {
        "Int" -> "number"
        "Float" -> "number"
        "Long" -> "number"

        "String" -> "string"
        "Unit" -> "any"
        else -> tsType
    }

    if (tsType.endsWith("DTO")) {
        dependencyCollection.add(kotlinType)
    }

    return tsType
}

fun kotlinTypeToNullableTsType(kotlinType: KType, dependencyCollection: DependencyCollection) =
    "${kotlinTypeToTsType(kotlinType, dependencyCollection)} ${if (kotlinType.isMarkedNullable) " | undefined" else ""}"
