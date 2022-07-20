package org.springframework.amqp.tutorials.tut3

import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.util.StopWatch

class Tut3Receiver {

    @RabbitListener(queues = ["#{autoDeleteQueue1.name}"])
    fun receive1(`in`: String) = receive(`in`, 1)

    @RabbitListener(queues = ["#{autoDeleteQueue2.name}"])
    fun receive2(`in`: String) = receive(`in`, 2)

    private fun receive(`in`: String, receiver: Int) {
        val watch = StopWatch()
        watch.start()
        println("instance $receiver [x] Received '$`in`'")

        doWork(`in`)
        watch.stop()
        println("instance $receiver [x] Done in ${watch.totalTimeSeconds}s")
    }

    private fun doWork(`in`: String) = `in`.toCharArray().forEach { ch ->
        if (ch == '.') Thread.sleep(1000)
    }
}
