package clean.architecture.example

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CleanArchitectureExampleApplication

fun main(args: Array<String>) {
	runApplication<CleanArchitectureExampleApplication>(*args)
}
