package org.springframework.amqp.tutorials.tut6

import org.springframework.amqp.core.DirectExchange
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.scheduling.annotation.Scheduled

class Tut6Client(
    private val template: RabbitTemplate,
    private val exchange: DirectExchange,
) {

    companion object {
        private var start = 0
    }

    @Scheduled(fixedDelay = 1000, initialDelay = 500)
    fun send() {
        println(" [x] Requesting fib($start)")
        val response = template.convertSendAndReceive(exchange.name, "rpc", start++) as Int?
        println(" [.] Got '$response'")
    }
}
