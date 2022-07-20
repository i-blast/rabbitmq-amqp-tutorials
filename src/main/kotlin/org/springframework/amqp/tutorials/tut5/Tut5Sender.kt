package org.springframework.amqp.tutorials.tut5

import org.springframework.amqp.core.TopicExchange
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.scheduling.annotation.Scheduled
import java.util.concurrent.atomic.AtomicInteger

class Tut5Sender(
    private val template: RabbitTemplate,
    private val topic: TopicExchange,
) {

    companion object {
        private val index = AtomicInteger(0)
        private val count = AtomicInteger(0)
        private val keys = arrayOf(
            "quick.orange.rabbit", "lazy.orange.elephant", "quick.orange.fox",
            "lazy.brown.fox", "lazy.pink.rabbit", "quick.brown.fox"
        )
    }

    @Scheduled(fixedDelay = 1000, initialDelay = 500)
    fun send() {
        if (index.incrementAndGet() == keys.size) index.set(0)
        val key = keys[index.get()]

        val message = buildString {
            append("Hello to ")
            append(key).append(' ')
            append(count.incrementAndGet())
        }

        template.convertAndSend(topic.name, key, message)
        println(" [x] Sent '$message'")
    }
}
