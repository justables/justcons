package de.ilijaz.skaagen

import java.util.*

class ChangeCase

fun toCamelCase(str: String): String {
  val list = toList(str).toMutableList()
  for ((i, item) in list.withIndex()) {
    if (i == 0) {
      continue
    }
    list[i] = item.substring(0, 1).uppercase() + item.substring(1)
  }
  return list.joinToString("")
}

fun toPascalCase(str: String): String =
  toList(str).joinToString("") { it.substring(0, 1).uppercase() + it.substring(1) }

fun toSnakeCase(str: String) = toSeparatedCase(str, "_")
fun toSentenceCase(str: String) = toSeparatedCase(str, " ")
fun toKebabCase(str: String) = toSeparatedCase(str, "-")

fun toSeparatedCase(str: String, separator: String) = toList(str).joinToString(separator)

private fun toList(str: String): List<String> {
  val result = ArrayList<String>()
  var lastChar: Char? = null
  var currentString = ""
  for (ch in str.iterator()) {
    if (lastChar == null) {
      currentString += ch
      lastChar = ch
      continue
    }
    if (!lastChar.isUpperCase() && ch.isUpperCase()) {
      lastChar = ch
      result += currentString
      currentString = "" + ch
      continue
    }
    lastChar = ch
    currentString += ch
    if (ch == '_' || ch == ' ' || ch == '-') {
      result += currentString
      currentString = "" + ch
      continue
    }
  }
  result += currentString
  return result.map { it.lowercase(Locale.getDefault()) }
}
