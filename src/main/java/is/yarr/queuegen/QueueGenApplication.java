package is.yarr.queuegen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

//@ComponentScan({"is.yarr.queuegen"})
@EnableAsync
// https://stackoverflow.com/a/41103594/3929546
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
public class QueueGenApplication {

    public static void main(String[] args) {
        SpringApplication.run(QueueGenApplication.class, args);
    }

    @Bean
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("Stuff-");
        executor.initialize();
        return executor;
    }
}
