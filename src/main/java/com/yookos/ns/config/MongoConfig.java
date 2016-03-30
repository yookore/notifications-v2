package com.yookos.ns.config;

import com.mongodb.MongoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by jome on 2016/02/04.
 */

@Configuration
public class MongoConfig {
    @Bean
    MongoClient mongoClient() {
        return new MongoClient("192.168.121.191");
    }
}
