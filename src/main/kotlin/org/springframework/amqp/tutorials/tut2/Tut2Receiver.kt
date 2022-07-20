package org.springframework.amqp.tutorials.tut2

import org.springframework.amqp.rabbit.annotation.RabbitHandler
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.util.StopWatch

@RabbitListener(queues = ["hello"])
class Tut2Receiver(
    private val instance: Int,
) {

    @RabbitHandler
    fun receive(`in`: String) {
        val watch = StopWatch()
        watch.start()
        println("instance $instance [x] Received '$`in`'")

        doWork(`in`)
        watch.stop()
        println("instance $instance [x] Done in ${watch.totalTimeSeconds}s")
    }

    private fun doWork(`in`: String) {
        `in`.toCharArray().forEach { ch ->
            if (ch == '.') Thread.sleep(1000)
        }
    }
}
