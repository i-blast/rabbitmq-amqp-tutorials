package org.springframework.amqp.tutorials.tut5

import org.springframework.amqp.core.*
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Profile("tut5", "topics")
@Configuration
class Tut5Config {

    @Bean
    fun connectionFactory(): ConnectionFactory {
        val connectionFactory = CachingConnectionFactory("localhost")
        connectionFactory.username = "guest"
        connectionFactory.setPassword("guest")
        return connectionFactory
    }

    @Bean
    fun rabbitTemplate(connectionFactory: ConnectionFactory) = RabbitTemplate(connectionFactory)

    @Bean
    fun topic() = TopicExchange("tut.topic")

    @Profile("receiver")
    companion object ReceiverConfig {

        @Bean
        fun receiver() = Tut5Receiver()

        @Bean
        fun autoDeleteQueue1(): Queue = AnonymousQueue()

        @Bean
        fun autoDeleteQueue2(): Queue = AnonymousQueue()

        @Bean
        fun binding1a(
            topic: TopicExchange,
            autoDeleteQueue1: Queue,
        ): Binding = BindingBuilder.bind(autoDeleteQueue1)
            .to(topic)
            .with("*.orange.*")

        @Bean
        fun binding1b(
            topic: TopicExchange,
            autoDeleteQueue1: Queue,
        ): Binding = BindingBuilder.bind(autoDeleteQueue1)
            .to(topic)
            .with("*.*.rabbit")

        @Bean
        fun binding2a(
            topic: TopicExchange,
            autoDeleteQueue2: Queue,
        ): Binding = BindingBuilder.bind(autoDeleteQueue2)
            .to(topic)
            .with("lazy.#")
    }

    @Profile("sender")
    @Bean
    fun sender(
        rabbitTemplate: RabbitTemplate,
        topicExchange: TopicExchange,
    ) = Tut5Sender(rabbitTemplate, topicExchange)
}
