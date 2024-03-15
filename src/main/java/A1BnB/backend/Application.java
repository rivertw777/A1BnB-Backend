package A1BnB.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.retry.annotation.EnableRetry;

@EnableAspectJAutoProxy
@EnableJpaAuditing
@SpringBootApplication
@EnableCaching
@EnableRetry
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
