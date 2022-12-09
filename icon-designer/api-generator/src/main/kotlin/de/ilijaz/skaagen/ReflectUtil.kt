package de.ilijaz.skaagen

import kotlin.reflect.KClass
import kotlin.reflect.KType

class ReflectUtil

fun getPackagePathOfKClass(kClass: KClass<out Any>) =
    removeRootPackage(kClass.java.`package`.toString()).split(".").toMutableList()

fun getPackagePathOfKClass(kType: KType): MutableList<String> {
    val packagePath =
        removeRootPackage(getNestedType(kType).toString()).split(".").toMutableList()
    packagePath.removeLast()
    return packagePath
}

fun getNestedType(kType: KType): KType {
    val firstArgument = kType.arguments.firstOrNull()
    return if (firstArgument != null) {
        getNestedType(firstArgument.type!!)
    } else {
        kType
    }
}

private fun removeRootPackage(path: String) = path.split(" ").last().substring("$rootPackage.".length)