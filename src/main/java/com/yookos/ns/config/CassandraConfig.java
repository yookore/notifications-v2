package com.yookos.ns.config;

import com.datastax.driver.core.AuthProvider;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.PlainTextAuthProvider;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import com.yookos.ns.domain.NotificationUser;
import com.yookos.ns.domain.YookoreNotificationItem;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by jome on 2016/02/04.
 */

@Configuration
public class CassandraConfig {
    @Bean
    Cluster cassandraCluster(){
        AuthProvider provider = new PlainTextAuthProvider("cassandra","Gonzo@7072");

        return Cluster.builder().addContactPoints("192.168.121.171", "192.168.121.172", "192.168.121.173", "192.168.121.225", "192.168.121.174").withAuthProvider(provider).build();

    }

    @Bean
    Session session(){
        return cassandraCluster().connect();
    }

    @Bean
    Mapper<YookoreNotificationItem> yookoreNotificationItemMapper(){
        return new MappingManager(session()).mapper(YookoreNotificationItem.class);
    }

    @Bean Mapper<NotificationUser> notificationUserMapper(){
        return new MappingManager(session()).mapper(NotificationUser.class);
    }
}
