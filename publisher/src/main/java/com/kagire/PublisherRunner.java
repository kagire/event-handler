package com.kagire;

import com.kagire.service.input.MessageGenerationMenu;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class PublisherRunner {

    public static void main(String[] args) {
        SpringApplication.run(PublisherRunner.class, args);
        MessageGenerationMenu.menu();
    }
}
