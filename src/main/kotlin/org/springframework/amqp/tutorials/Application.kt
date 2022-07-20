package org.springframework.amqp.tutorials

import org.springframework.amqp.tutorials.tut1.RabbitAmqpTutorialsRunner
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Profile
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@SpringBootApplication
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}

@Profile("usage_message")
@Bean
fun usage() = CommandLineRunner {
    println("This app uses Spring Profiles to control its behavior.\n")
    println("Sample usage: java -jar rabbit-tutorials.jar --spring.profiles.active=hello-world,sender")
}

@Profile("!usage_message")
@Bean
fun tutorial(): CommandLineRunner = RabbitAmqpTutorialsRunner()
