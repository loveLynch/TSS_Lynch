package com.silver.tss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


/**
 * UESTC-SICE-108
 * Topic Selection System
 *
 * @author Yuchen_Chiang
 */
@EnableJpaAuditing
@SpringBootApplication
public class TssApplication {

    public static void main(String[] args) {
        SpringApplication.run(TssApplication.class, args);
    }
}
