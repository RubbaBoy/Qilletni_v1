package is.yarr.queuegen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan({"is.yarr.queuegen.rest"})
// https://stackoverflow.com/a/41103594/3929546
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
public class QueueGenApplication {

    public static void main(String[] args) {
        SpringApplication.run(QueueGenApplication.class, args);
    }

}
