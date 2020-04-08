package com.google.shinyay

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringBootKubernetesGsApplication

fun main(args: Array<String>) {
	runApplication<SpringBootKubernetesGsApplication>(*args)
}
