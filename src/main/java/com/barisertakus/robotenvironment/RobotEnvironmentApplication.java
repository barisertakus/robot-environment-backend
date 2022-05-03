package com.barisertakus.robotenvironment;

import com.barisertakus.robotenvironment.service.RobotService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class RobotEnvironmentApplication {

    public static void main(String[] args) {
        SpringApplication.run(RobotEnvironmentApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner (RobotService robotService){
        return args -> robotService.saveFirstRobot();
    };
}
