package kafkastore.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("kafkastore")
public class KafkaStoreApi {

	public static void main(String[] args) {
		SpringApplication.run(KafkaStoreApi.class, args);
	}
}
