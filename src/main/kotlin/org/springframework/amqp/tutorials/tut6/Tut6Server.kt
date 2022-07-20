package org.springframework.amqp.tutorials.tut6

import org.springframework.amqp.rabbit.annotation.RabbitListener

class Tut6Server {

    @RabbitListener(queues = ["tut.rpc.requests"])
    fun fibonacci(n: Int): Int {
        println(" [x] Received request for $n")
        val result = fib(n)
        println(" [.] Returned $result")
        return result
    }

    private fun fib(n: Int): Int = if (n == 0) 0 else if (n == 1) 1 else fib(n - 1) + fib(n - 2)
}
