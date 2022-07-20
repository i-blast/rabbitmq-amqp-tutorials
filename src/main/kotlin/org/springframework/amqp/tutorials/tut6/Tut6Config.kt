package org.springframework.amqp.tutorials.tut6

import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder

import org.springframework.amqp.core.DirectExchange
import org.springframework.amqp.core.Queue
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Profile("tut6", "rpc")
@Configuration
class Tut6Config {

    @Bean
    fun connectionFactory(): ConnectionFactory {
        val connectionFactory = CachingConnectionFactory("localhost")
        connectionFactory.username = "guest"
        connectionFactory.setPassword("guest")
        return connectionFactory
    }

    @Bean
    fun rabbitTemplate(connectionFactory: ConnectionFactory) = RabbitTemplate(connectionFactory)

    @Profile("client")
    object ClientConfig {
        @Bean
        fun exchange() = DirectExchange("tut.rpc")

        @Bean
        fun client(
            rabbitTemplate: RabbitTemplate,
            exchange: DirectExchange,
        ) = Tut6Client(rabbitTemplate, exchange)
    }

    @Profile("server")
    object ServerConfig {
        @Bean
        fun queue() = Queue("tut.rpc.requests")

        @Bean
        fun exchange() = DirectExchange("tut.rpc")

        @Bean
        fun binding(
            exchange: DirectExchange,
            queue: Queue,
        ): Binding = BindingBuilder.bind(queue)
            .to(exchange)
            .with("rpc")

        @Bean
        fun server() = Tut6Server()
    }
}
