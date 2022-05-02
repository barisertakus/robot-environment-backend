package com.barisertakus.robotenvironment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class RobotEnvironmentApplication {

    public static void main(String[] args) {
        SpringApplication.run(RobotEnvironmentApplication.class, args);
    }

}
