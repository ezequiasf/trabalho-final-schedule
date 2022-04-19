package com.dbccompany.kafkareceita;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class KafkareceitaApplication {

    public static void main(String[] args) {
        SpringApplication.run(KafkareceitaApplication.class, args);
    }

}
