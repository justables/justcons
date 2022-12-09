package de.ilijaz.skaagen.service.method

import de.ilijaz.skaagen.DependencyCollection
import de.ilijaz.skaagen.kotlinTypeToTsType
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import kotlin.reflect.KFunction
import kotlin.reflect.full.findAnnotation

private const val PATH_VARIABLE_ANNOTATION_NAME = "PathVariable"
private const val REQUEST_BODY_ANNOTATION_NAME = "RequestBody"

private const val escape = "$"

class ServiceMethod(private val function: KFunction<*>, private val dependencyCollection: DependencyCollection) {

  fun toCode(): String {
    val restMapping = getRestMapping() ?: return ""
    return """

  ${getName()}(${getParameters()}): Observable<${getReturnType()}> {
    return this.http.${restMapping.angularHttpFunctionName}<${getReturnType()}>(`$escape{BASE_URL}${getPath(restMapping)}`${getBody()})
  }"""
  }

  private fun getName() = function.name

  private fun getParameters() = function.parameters
    .filter { it.name != null }
    .filter {
      it.annotations
        .filter { it.annotationClass.simpleName != null }
        .map { it.annotationClass.simpleName!! }
        .find { (it.contains(PATH_VARIABLE_ANNOTATION_NAME) || it.contains(REQUEST_BODY_ANNOTATION_NAME)) } != null
    }
    .joinToString(", ") { "${it.name}: ${kotlinTypeToTsType(it.type, dependencyCollection)}" }

  private fun getRestMapping(): RestMapping? {
    for (annotation in function.annotations) {
      for (restMapping in RestMapping.values()) {
        if (annotation.annotationClass.simpleName.equals(restMapping.annotationName)) {
          return restMapping
        }
      }
    }
    return null
  }

  private fun getReturnType() = kotlinTypeToTsType(function.returnType, dependencyCollection)

  private fun getPath(restMapping: RestMapping): String {
    var path = ""
    path = when (restMapping) {
      RestMapping.GET -> function.findAnnotation<GetMapping>()!!.value[0]
      RestMapping.POST -> function.findAnnotation<PostMapping>()!!.value[0]
      RestMapping.PUT -> function.findAnnotation<PutMapping>()!!.value[0]
      RestMapping.DELETE -> function.findAnnotation<DeleteMapping>()!!.value[0]
    }
    return path.replace("{", "$escape{")
  }


  private fun getBody(): String {
    val bodyParamName = function.parameters
      .filter { it.name != null }
      .filter {
        it.annotations.find {
          it.annotationClass.simpleName != null && it.annotationClass.simpleName!!.contains("RequestBody")
        } != null
      }
      .joinToString(", ") { it.name!! }

    if (bodyParamName.isBlank()) {
      return ""
    }

    return ", $bodyParamName"
  }
}
