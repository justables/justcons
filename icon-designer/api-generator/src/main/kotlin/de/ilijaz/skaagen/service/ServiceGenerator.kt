package de.ilijaz.skaagen.service

import de.ilijaz.skaagen.*
import de.ilijaz.skaagen.service.method.ServiceMethod
import java.io.File
import kotlin.reflect.KClass
import kotlin.reflect.full.functions

class ServiceGenerator(private val controller: KClass<out Any>) {

    private val path = getPackagePathOfKClass(controller)
    private val dependencyCollection = DependencyCollection(path)

    init {
        dependencyCollection.add("BASE_URL", listOf("config"))
    }

    fun writeFile() {
        val fileName = toKebabCase(controller.simpleName!!).replace("-controller", ".service.ts")
        val path =
            listOf(
                targetPath,
                controller.java.`package`.name.replace("$rootPackage.", "").replace(".", "/"),
                fileName
            ).joinToString("/")
        val code = toCode()
        File(path).parentFile.mkdirs()
        File(path).writeText(code)
    }

    private fun toCode(): String {
        val methods = getMethods()
        val dependencies = dependencyCollection.toCode()
        return """/** generated code */

import { Injectable } from '@angular/core';

import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

$dependencies

@Injectable({
    providedIn: 'root',
})
export class ${getServiceName()} {
  constructor(protected http: HttpClient) {}$methods
}
"""
    }

    private fun getServiceName() = controller.simpleName?.replace("Controller", "Service")

    private fun getMethods() =
        controller.functions.joinToString("") { ServiceMethod(it, dependencyCollection).toCode() }

}
