package org.springframework.amqp.tutorials.tut4

import org.springframework.amqp.core.*
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Profile("tut4", "routing")
@Configuration
class Tut4Config {

    @Bean
    fun connectionFactory(): ConnectionFactory {
        val connectionFactory = CachingConnectionFactory("localhost")
        connectionFactory.username = "guest"
        connectionFactory.setPassword("guest")
        return connectionFactory
    }

    @Bean
    fun rabbitTemplate() = RabbitTemplate(connectionFactory())

    @Bean("direct")
    fun direct() = DirectExchange("tut.direct")

    @Profile("receiver")
    companion object ReceiverConfig {

        @Bean
        fun autoDeleteQueue1(): Queue = AnonymousQueue()

        @Bean
        fun autoDeleteQueue2(): Queue = AnonymousQueue()

        @Bean
        fun binding1a(
            direct: DirectExchange,
            autoDeleteQueue1: Queue,
        ): Binding = BindingBuilder.bind(autoDeleteQueue1)
            .to(direct)
            .with("orange")

        @Bean
        fun binding1b(
            direct: DirectExchange,
            autoDeleteQueue1: Queue,
        ): Binding = BindingBuilder.bind(autoDeleteQueue1)
            .to(direct)
            .with("black")

        @Bean
        fun binding2a(
            direct: DirectExchange,
            autoDeleteQueue2: Queue,
        ): Binding = BindingBuilder.bind(autoDeleteQueue2)
            .to(direct)
            .with("green")

        @Bean
        fun binding2b(
            direct: DirectExchange,
            autoDeleteQueue2: Queue,
        ): Binding = BindingBuilder.bind(autoDeleteQueue2)
            .to(direct)
            .with("black")

        @Bean
        fun receiver() = Tut4Receiver()
    }

    @Profile("sender")
    @Bean
    fun sender(
        rabbitTemplate: RabbitTemplate,
        directExchange: DirectExchange
    ) = Tut4Sender(rabbitTemplate, directExchange)
}
