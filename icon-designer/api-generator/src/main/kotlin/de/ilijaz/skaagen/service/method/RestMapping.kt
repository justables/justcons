package de.ilijaz.skaagen.service.method

enum class RestMapping(val annotationName: String, val angularHttpFunctionName: String) {
  GET("GetMapping", "get"),
  POST("PostMapping", "post"),
  PUT("PutMapping", "put"),
  DELETE("DeleteMapping", "delete"),


}
