package kafkastore.event.topology;

import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;
import kafkastore.event.Employee;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KStreamBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

import static io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG;
import static org.apache.kafka.streams.StreamsConfig.APPLICATION_ID_CONFIG;
import static org.apache.kafka.streams.StreamsConfig.BOOTSTRAP_SERVERS_CONFIG;

@Configuration
public class OrderTopology {


    @Bean
    public boolean createToplogy(){

        final Serde<String> stringSerde = Serdes.String();

        final KStreamBuilder builder = new KStreamBuilder();

        new Employee();

        SpecificAvroSerde avroSerde = new SpecificAvroSerde<Employee>();
        avroSerde.configure(getProperties(),false);

        builder.stream(stringSerde, stringSerde, "debezium.store.orders")
                .map((key, value)  -> new KeyValue<>("1",some()))
                .to(stringSerde, avroSerde, "sink.store.orders");

        final KafkaStreams payloadStream = new KafkaStreams(builder, getProperties());

        payloadStream.start();

        return false;
    }

    private Properties getProperties(){
        Properties streamsConfiguration = new Properties();

        streamsConfiguration.put(APPLICATION_ID_CONFIG, "kafkastore");
        streamsConfiguration.put(BOOTSTRAP_SERVERS_CONFIG, "kafka:9092");
        streamsConfiguration.put(SCHEMA_REGISTRY_URL_CONFIG, "http://schema:8081");

        return streamsConfiguration;
    }

    private Employee some(){

        return Employee
                .newBuilder()
                .setAge(1)
                .setFirstName("Diego")
                .setLastName("Bonfim")
                .setPhoneNumber("011")
                .build();
    }
}
