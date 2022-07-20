package org.springframework.amqp.tutorials.tut2

import org.springframework.amqp.core.Queue
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Profile("tut2", "work-queues")
@Configuration
class Tut2Config {

    @Bean
    fun connectionFactory(): ConnectionFactory {
        val connectionFactory = CachingConnectionFactory("localhost")
        connectionFactory.username = "guest"
        connectionFactory.setPassword("guest")
        return connectionFactory
    }

    @Bean("rabbitTemplate")
    fun rabbitTemplate() = RabbitTemplate(connectionFactory())

    @Bean("queue")
    fun hello() = Queue("hello")

    @Profile("receiver")
    private companion object ReceiverConfig {
        @Bean
        fun receiver1() = Tut2Receiver(1)

        @Bean
        fun receiver2() = Tut2Receiver(2)
    }

    @Profile("sender")
    @Bean
    fun sender(
        @Qualifier("rabbitTemplate") rabbitTemplate: RabbitTemplate,
        @Qualifier("queue") queue: Queue,
    ) = Tut2Sender(rabbitTemplate, queue)
}
