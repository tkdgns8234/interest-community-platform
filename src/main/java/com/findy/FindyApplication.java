package com.findy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class FindyApplication {

    public static void main(String[] args) {
        SpringApplication.run(FindyApplication.class, args);
    }

}
