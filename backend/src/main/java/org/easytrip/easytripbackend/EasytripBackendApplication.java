package org.easytrip.easytripbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class EasytripBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(EasytripBackendApplication.class, args);
    }

}
