package kafkastore.event.consumer;

import io.confluent.kafka.streams.serdes.avro.SpecificAvroDeserializer;
import kafkastore.domain.model.Order;
import kafkastore.event.OrderAvro;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.Properties;
import java.util.UUID;

import static io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG;
import static org.apache.kafka.streams.StreamsConfig.BOOTSTRAP_SERVERS_CONFIG;

@Configuration
public class OrderEventConsumer {

    private Logger log = LoggerFactory.getLogger(OrderEventConsumer.class);

    @Bean
    public boolean consumer(){
        ModelMapper modelMapper = new ModelMapper();

        Properties props = new Properties();
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "KafkaExampleAvroConsumer" + UUID.randomUUID());
        props.put(BOOTSTRAP_SERVERS_CONFIG, "kafka:9092");
        props.put(SCHEMA_REGISTRY_URL_CONFIG, "http://schema:8081");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, SpecificAvroDeserializer.class.getName());
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        final Consumer<String, OrderAvro> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList("sink.store.orders"));

        try {
            while (true) {
                ConsumerRecords<String, OrderAvro> records = consumer.poll(1000);
                for (ConsumerRecord<String, OrderAvro> record : records)
                    log.info("Order event Consumer " + modelMapper.map(record.value(), Order.class).toString());
            }
        } finally {
            consumer.close();
        }

    }
}
