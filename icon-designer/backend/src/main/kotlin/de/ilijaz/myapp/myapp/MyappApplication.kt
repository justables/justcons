package de.ilijaz.myapp.myapp

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MyappApplication

fun main(args: Array<String>) {
	runApplication<MyappApplication>(*args)
}
