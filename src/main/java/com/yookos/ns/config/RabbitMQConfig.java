package com.yookos.ns.config;

import com.google.gson.Gson;
import com.yookos.ns.consumers.NotificationReceiver;
import com.yookos.ns.consumers.PushNotificationReceiver;
import com.yookos.ns.services.ServiceUtils;
import org.codehaus.jackson.map.ObjectMapper;
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
import org.springframework.amqp.support.converter.JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


/**
 * Created by jome on 2016/01/19.
 */

@Configuration
@PropertySource("classpath:config.properties")
public class RabbitMQConfig {
    //    String notificationQueueName = "myqueue";
    String pclNotificationQueueName = "ns_blogpost_pcl_queue";
    String notificationQueueName;

    String userEmailQueue = "user_email_verify_notify_queue";
    String resetEmailQueue = "reset_user_password_email_notify_queue";
    String passwordEmailQueue = "password_change_email_notify_queue";
    String updateEmailQueue = "update_primary_email_notify_queue";
    String friendRequestQueue = "ns_relationship_queue";
    String activityQueue = "pps_activity_queue";
    String statusUpdateQueue = "ns_statusupdate_creation_queue";
    String groupInvitationQueue = "ns_group_invitation_queue";
    String photoCreationQueue = "ns_photo_creation_queue";
    String videoCreationQueue = "ns_video_creation_queue";
    String audioCreationQueue = "ns_audio_creation_queue";
    String commentCreationQueue = "ns_comment_creation_queue";
    String newpostNotificationQueue = "ns_newpost_creation_queue";

    Logger log = LoggerFactory.getLogger(this.getClass());

    public RabbitMQConfig() {
        if (System.getenv().containsKey("PLATFORM")) {
            notificationQueueName = "a.new.ns.blogpost.queue";
        } else {
//            notificationQueueName = commentCreationQueue;
            notificationQueueName = "direct_messaging_queue";
        }
    }

    @Bean
    ConnectionFactory connectionFactory() {
        CachingConnectionFactory factory;
        if (System.getenv().containsKey("PLATFORM")) {
            log.info("Using prod rabbit");
            factory = new CachingConnectionFactory("192.168.121.154");
            factory.setUsername("yookore");
            factory.setPassword("Wordpass15");
        } else {
            log.info("Using local rabbit");
            factory = new CachingConnectionFactory("localhost");
            factory.setUsername("guest");
            factory.setPassword("guest");
        }
        return factory;
    }

    @Bean
    Queue myQueue() {
        return new Queue(notificationQueueName, true);
    }

    @Bean
    Queue pclQueue() {
        return new Queue(pclNotificationQueueName, true);
    }

    @Bean
    DirectExchange notificationExchange() {
        return new DirectExchange("myexchange");
    }

    @Bean
    DirectExchange pclNotificationExchange() {
        return new DirectExchange("blogpost_exchange");
    }

    @Bean
    Binding binding(Queue myQueue, DirectExchange notificationExchange) {
        return BindingBuilder.bind(myQueue).to(notificationExchange).with(notificationQueueName);
    }

    @Bean
    Binding pclBinding(Queue pclQueue, DirectExchange pclNotificationExchange) {
        return BindingBuilder.bind(pclQueue).to(pclNotificationExchange).with(pclNotificationQueueName);
    }

    @Bean
    JsonMessageConverter jsonMessageConverter() {
        JsonMessageConverter converter = new JsonMessageConverter();
        converter.setJsonObjectMapper(new ObjectMapper());
        return converter;
    }

    @Bean
    RabbitTemplate rabbitTemplate() {
        CachingConnectionFactory factory;
        if (System.getenv().containsKey("PLATFORM")) {
            factory = new CachingConnectionFactory("192.168.121.155");
            factory.setPassword("Wordpass15");
            factory.setUsername("yookore");

        } else {
            factory = new CachingConnectionFactory("localhost");
            factory.setPassword("guest");
            factory.setUsername("guest");

        }
        RabbitTemplate template = new RabbitTemplate(factory);
        template.setMessageConverter(jsonMessageConverter());
        return template;
    }

    @Bean
    NotificationReceiver receiver() {
        return new NotificationReceiver();
    }

    @Bean
    PushNotificationReceiver pushNotificationReceiver() {
        return new PushNotificationReceiver();
    }

    @Bean
    MessageListenerAdapter listenerAdapter(NotificationReceiver receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessage");

    }


    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory, NotificationReceiver receiver) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(pclNotificationQueueName, newpostNotificationQueue,
                notificationQueueName, friendRequestQueue, videoCreationQueue);
        container.setMessageListener(receiver);
        return container;

    }


    @Bean
    Gson gson() {
        return new Gson();
    }

    @Bean
    ServiceUtils serviceUtils() {
        return new ServiceUtils();
    }
}
