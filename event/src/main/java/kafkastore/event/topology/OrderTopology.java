package kafkastore.event.topology;

import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroDeserializer;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;
import kafkastore.domain.model.Order;
import kafkastore.event.OrderAvro;
import kafkastore.event.mapper.OrderEventMapper;
import kafkastore.event.resources.debezium.EventDebezium;
import kafkastore.event.resources.debezium.OrderDebezium;
import kafkastore.event.resources.serializer.JsonPOJODeserializer;
import kafkastore.event.resources.serializer.JsonPOJOSerializer;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KStreamBuilder;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.Objects;
import java.util.Properties;
import java.util.UUID;

import static io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG;
import static org.apache.kafka.streams.StreamsConfig.APPLICATION_ID_CONFIG;
import static org.apache.kafka.streams.StreamsConfig.BOOTSTRAP_SERVERS_CONFIG;

@Configuration
public class OrderTopology {

    private Logger log = LoggerFactory.getLogger(OrderTopology.class);

    @Autowired
    private OrderEventMapper orderEventMapper;

    //@Bean
    public boolean createToplogy(){
        final Serde<String> stringSerde = Serdes.String();

        final KStreamBuilder builder = new KStreamBuilder();

        final Serde<EventDebezium<OrderDebezium>> serdeInput =
                Serdes.serdeFrom(new JsonPOJOSerializer(), new JsonPOJODeserializer(EventDebezium.class, OrderDebezium.class));

        SpecificAvroSerde serdeOutput = new SpecificAvroSerde();
        serdeOutput.configure(getProperties(),false);

        builder.stream(stringSerde, serdeInput, "debezium.store.orders")
                .filter((key, event) -> !event.isDelete())
                .filter((key, event) -> IsNewEvent(event))
                .map((key, value)  -> new KeyValue<>(key, orderEventMapper.map(value)))
                .to(stringSerde, serdeOutput, "sink.store.orders");

        final KafkaStreams payloadStream = new KafkaStreams(builder, getProperties());
        payloadStream.start();

        return false;
    }

    //Avoid duplicated events
    private boolean IsNewEvent(EventDebezium<OrderDebezium> event) {
        return !(event.isUpdate()
                && Objects.equals(event.getBefore().getStatus(),event.getAfter().getStatus()));
    }

    private Properties getProperties(){
        Properties streamsConfiguration = new Properties();

        streamsConfiguration.put(APPLICATION_ID_CONFIG, "kafkastore");
        streamsConfiguration.put(BOOTSTRAP_SERVERS_CONFIG, "kafka:9092");
        streamsConfiguration.put(SCHEMA_REGISTRY_URL_CONFIG, "http://schema:8081");

        return streamsConfiguration;
    }

    @Bean
    public boolean consumer(){
            ModelMapper modelMapper = new ModelMapper();

            Properties props = getProperties();
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
                        log.info(record.offset() + ": " + modelMapper.map(record.value(), Order.class).toString());
                }
            } finally {
                consumer.close();
            }
    }
}
