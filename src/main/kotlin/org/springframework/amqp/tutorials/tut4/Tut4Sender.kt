package org.springframework.amqp.tutorials.tut4

import org.springframework.amqp.core.DirectExchange
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.scheduling.annotation.Scheduled
import java.util.concurrent.atomic.AtomicInteger

class Tut4Sender(
    private val template: RabbitTemplate,
    private val direct: DirectExchange,
) {

    companion object {
        private val index = AtomicInteger(0)
        private val count = AtomicInteger(0)
        private val keys = arrayOf("orange", "black", "green")
    }

    @Scheduled(fixedDelay = 1000, initialDelay = 500)
    fun send() {
        if (index.incrementAndGet() == 3) index.set(0)
        val key = keys[index.get()]

        val message = buildString {
            append("Hello to ")
            append(key).append(' ')
            append(count.get())
        }

        template.convertAndSend(direct.name, key, message)
        println(" [x] Sent '$message'")
    }
}
