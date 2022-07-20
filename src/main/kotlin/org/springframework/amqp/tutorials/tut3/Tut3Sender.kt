package org.springframework.amqp.tutorials.tut3

import org.springframework.amqp.core.FanoutExchange
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.scheduling.annotation.Scheduled
import java.util.concurrent.atomic.AtomicInteger

class Tut3Sender(
    private val template: RabbitTemplate,
    private val fanout: FanoutExchange,
) {

    companion object {
        private val dots = AtomicInteger(0)
        private val count = AtomicInteger(0)
    }

    @Scheduled(fixedDelay = 1000, initialDelay = 500)
    fun send() {
        if (dots.incrementAndGet() == 3) dots.set(1)
        val message = buildString {
            append("Hello")
            repeat(dots.get()) {
                append('.')
            }
            append(count.incrementAndGet())
        }

        template.convertAndSend(fanout.name, "", message)
        println(" [x] Sent '$message'")
    }
}
