package kafkastore.event;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan("kafkastore")
@EnableJpaRepositories("kafkastore")
@EntityScan("kafkastore")
public class KafkaStoreEvent {

	public static void main(String[] args) {
		SpringApplication.run(KafkaStoreEvent.class, args);
	}
}
