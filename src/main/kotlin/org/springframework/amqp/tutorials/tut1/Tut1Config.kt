package org.springframework.amqp.tutorials.tut1

import org.springframework.amqp.core.Queue
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Profile("tut1", "hello-world")
@Configuration
class Tut1Config {

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
    @Bean
    fun receiver() = Tut1Receiver()

    @Profile("sender")
    @Bean
    fun sender(
        @Qualifier("rabbitTemplate") rabbitTemplate: RabbitTemplate,
        @Qualifier("queue") queue: Queue,
    ) = Tut1Sender(rabbitTemplate, queue)
}
