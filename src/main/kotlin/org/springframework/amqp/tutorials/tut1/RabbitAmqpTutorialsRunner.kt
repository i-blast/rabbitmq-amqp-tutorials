package org.springframework.amqp.tutorials.tut1

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.CommandLineRunner
import org.springframework.context.ConfigurableApplicationContext

class RabbitAmqpTutorialsRunner : CommandLineRunner {

    private lateinit var ctx: ConfigurableApplicationContext

    @Value("\${tutorial.client.duration:0}")
    private val duration = 0L

    override fun run(vararg arg0: String?) {
        println("Ready ... running for " + duration + "ms")
        Thread.sleep(duration)
        ctx.close()
    }
}
