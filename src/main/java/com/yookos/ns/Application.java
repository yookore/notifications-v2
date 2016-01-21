package com.yookos.ns;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;

import java.util.Map;

/**
 * Created by jome on 2016/01/19.
 */

@SpringBootApplication
@PropertySources({@PropertySource("classpath:app.properties")})
public class Application implements CommandLineRunner {
    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    Environment env;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

    }

    public void testProperties() {
        log.info("Starting project...");
        String property = env.getProperty("yookos.project.name");
        log.info(property);
        Map<String, String> getenv = System.getenv();
        for (String key : getenv.keySet()) {
            log.info("{} : {}", key, getenv.get(key));
        }

        String[] profiles = env.getDefaultProfiles();
        for (String str : profiles) {
            log.info(str);
        }

    }

    @Override
    public void run(String... args) throws Exception {
        testProperties();
    }
}
