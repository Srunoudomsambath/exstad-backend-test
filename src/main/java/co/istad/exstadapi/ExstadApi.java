package co.istad.exstadapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorProvider", modifyOnCreate = false)
public class ExstadApi {

    public static void main(String[] args) {
        SpringApplication.run(ExstadApi.class, args);
    }

}
