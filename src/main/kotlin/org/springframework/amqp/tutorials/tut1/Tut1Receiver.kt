package org.springframework.amqp.tutorials.tut1

import org.springframework.amqp.rabbit.annotation.RabbitHandler
import org.springframework.amqp.rabbit.annotation.RabbitListener

@RabbitListener(queues = ["hello"])
class Tut1Receiver {

    @RabbitHandler
    fun receive(`in`: String) = println(" [x] Received '$`in`'")
}
