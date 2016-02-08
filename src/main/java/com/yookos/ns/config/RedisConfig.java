package com.yookos.ns.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by jome on 2016/02/04.
 */
@Configuration
public class RedisConfig {
    private static final String REDIS_HOST_1 = "192.168.121.165";
    private static final String REDIS_HOST_2 = "192.168.121.166";
    private static final String REDIS_HOST_3 = "192.168.121.167";

    @Bean
    JedisCluster jedisCluster() {
        Set<HostAndPort> nodes = new HashSet<>();
        nodes.add(new HostAndPort(REDIS_HOST_1, 6379));
        nodes.add(new HostAndPort(REDIS_HOST_2, 6379));
        nodes.add(new HostAndPort(REDIS_HOST_3, 6379));

        return new JedisCluster(nodes);
    }
}
