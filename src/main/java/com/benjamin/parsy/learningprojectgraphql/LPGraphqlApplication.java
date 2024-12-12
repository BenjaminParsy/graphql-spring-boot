package com.benjamin.parsy.learningprojectgraphql;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

@SpringBootApplication
@Slf4j
public class LPGraphqlApplication implements CommandLineRunner {

    private final Environment environment;

    public LPGraphqlApplication(Environment environment) {
        this.environment = environment;
    }

    public static void main(String[] args) {
        SpringApplication.run(LPGraphqlApplication.class, args);
    }

    @Override
    public void run(String... args) {

        try {

            InetAddress inetAddress = InetAddress.getLocalHost();
            String host = inetAddress.getHostAddress();

            String graphiqlPath = environment.getRequiredProperty("spring.graphql.graphiql.path");
            String serverPort = environment.getRequiredProperty("server.port");
            String h2Path = environment.getRequiredProperty("spring.h2.console.path");

            log.info("Qraphiql interface launch on url : http://{}:{}{}", host, serverPort, graphiqlPath);
            log.info("H2 interface launch on url : http://{}:{}{}", host, serverPort, h2Path);

        } catch (UnknownHostException e) {
            log.error("An error occurred while retrieving urls", e);
        }

    }

}
