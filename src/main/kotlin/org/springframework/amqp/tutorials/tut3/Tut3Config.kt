package org.springframework.amqp.tutorials.tut3

import org.springframework.amqp.core.*
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Profile("tut3", "pub-sub", "publish-subscribe")
@Configuration
class Tut3Config {

    @Bean
    fun connectionFactory(): ConnectionFactory {
        val connectionFactory = CachingConnectionFactory("localhost")
        connectionFactory.username = "guest"
        connectionFactory.setPassword("guest")
        return connectionFactory
    }

    @Bean("rabbitTemplate")
    fun rabbitTemplate() = RabbitTemplate(connectionFactory())

    @Bean("fanoutExchange")
    fun fanout(): FanoutExchange = FanoutExchange("tut.fanout")

    @Profile("receiver")
    private companion object ReceiverConfig {
        @Bean("autoDeleteQueue1")
        fun autoDeleteQueue1(): Queue = AnonymousQueue()

        @Bean("autoDeleteQueue2")
        fun autoDeleteQueue2(): Queue = AnonymousQueue()

        @Bean
        fun binding1(
            @Qualifier("fanoutExchange") fanout: FanoutExchange,
            @Qualifier("autoDeleteQueue1") autoDeleteQueue1: Queue,
        ): Binding = BindingBuilder.bind(autoDeleteQueue1).to(fanout)

        @Bean
        fun binding2(
            @Qualifier("fanoutExchange") fanout: FanoutExchange,
            @Qualifier("autoDeleteQueue2") autoDeleteQueue2: Queue,
        ): Binding = BindingBuilder.bind(autoDeleteQueue2).to(fanout)

        @Bean
        fun receiver() = Tut3Receiver()
    }

    @Profile("sender")
    @Bean
    fun sender(
        @Qualifier("rabbitTemplate") template: RabbitTemplate,
        @Qualifier("fanoutExchange") fanout: FanoutExchange,
    ) = Tut3Sender(template, fanout)
}
