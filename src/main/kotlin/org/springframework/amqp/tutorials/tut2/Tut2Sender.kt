package org.springframework.amqp.tutorials.tut2

import org.springframework.amqp.core.Queue
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.scheduling.annotation.Scheduled
import java.util.concurrent.atomic.AtomicInteger

class Tut2Sender(
    private val template: RabbitTemplate,
    private val queue: Queue,
) {

    companion object {
        private val dots = AtomicInteger(0)
        private val count = AtomicInteger(0)
    }

    @Scheduled(fixedDelay = 1000, initialDelay = 500)
    fun send() {
        if (dots.incrementAndGet() == 4) dots.set(1)
        val message = buildString {
            append("Hello")
            repeat(dots.get()) {
                append('.')
            }
            append(count.incrementAndGet())
        }

        template.convertAndSend(queue.name, message)
        println(" [x] Sent '$message'")
    }
}
