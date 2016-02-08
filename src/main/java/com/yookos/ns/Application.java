package com.yookos.ns;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import com.yookos.ns.domain.YookoreNotificationItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;

import java.util.UUID;

/**
 * Created by jome on 2016/01/19.
 */

@SpringBootApplication
@PropertySources({@PropertySource("classpath:app.properties")})
public class Application implements CommandLineRunner {
    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    Environment env;

    @Autowired
    Session session;

    @Autowired
    Mapper<YookoreNotificationItem> mapper;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

    }

    public void testProperties() {
        //dcaba371-05a6-4aa2-829e-7c55c8f2c783

        YookoreNotificationItem item = mapper.get(UUID.fromString("dcaba371-05a6-4aa2-829e-7c55c8f2c783"));
        log.info("Notification item: {}", item);
        ResultSet resultSet = session.execute("select * from notifications.notifications limit 10");

        for (Row row : resultSet) {
            log.info(row.toString());
            Object object = row.getObject(0);
            row.getColumnDefinitions().getName(0);
            log.info("Object: {}, Column name: {}", object.toString(), row.getColumnDefinitions().getName(0));
        }
    }

    @Override
    public void run(String... args) throws Exception {
//        testProperties();
        log.info("Starting Application");
    }
}
