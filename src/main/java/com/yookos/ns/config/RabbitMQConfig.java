package com.yookos.ns.config;

import com.yookos.ns.consumers.NotificationReceiver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

/**
 * Created by jome on 2016/01/19.
 */

@Configuration
@PropertySource("classpath:config.properties")
public class RabbitMQConfig {
    String notificationQueueName = "myqueue";
    Logger log = LoggerFactory.getLogger(this.getClass());

    @Bean
    ConnectionFactory connectionFactory() {
        CachingConnectionFactory factory = new CachingConnectionFactory("192.168.121.154");
        factory.setUsername("yookore");
        factory.setPassword("Wordpass15");
        return factory;
    }

    @Bean
    Queue myQueue() {
        return new Queue(notificationQueueName, true);
    }

    @Bean
    DirectExchange notificationExchange() {
        return new DirectExchange("myexchange");
    }

    @Bean
    Binding bindin(Queue myQueue, DirectExchange notificationExchange) {
        return BindingBuilder.bind(myQueue).to(notificationExchange).with(notificationQueueName);
    }

    @Bean
    RabbitTemplate rabbitTemplate() {
        return new RabbitTemplate(connectionFactory());
    }

    @Bean
    NotificationReceiver receiver() {
        return new NotificationReceiver();
    }

    @Bean
    MessageListenerAdapter listenerAdapter(NotificationReceiver receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessage");

    }

    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory, NotificationReceiver receiver) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(notificationQueueName);
        container.setMessageListener(receiver);
        return container;

    }

}
