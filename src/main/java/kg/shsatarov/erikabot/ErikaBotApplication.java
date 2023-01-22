package kg.shsatarov.erikabot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ErikaBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(ErikaBotApplication.class, args);


    }

}
