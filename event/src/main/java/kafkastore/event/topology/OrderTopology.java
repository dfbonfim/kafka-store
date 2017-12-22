package kafkastore.event.topology;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KStreamBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

import static org.apache.kafka.streams.StreamsConfig.APPLICATION_ID_CONFIG;
import static org.apache.kafka.streams.StreamsConfig.BOOTSTRAP_SERVERS_CONFIG;
import static org.apache.kafka.streams.StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG;
import static org.apache.kafka.streams.StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG;

@Configuration
public class OrderTopology {


    @Bean
    public boolean createToplogy(){

        final Serde<String> stringSerde = Serdes.String();

        final KStreamBuilder builder = new KStreamBuilder();

        builder.stream(stringSerde, stringSerde, "input")
                .map((key, value)  -> new KeyValue<>("1",value))
                .to(stringSerde, stringSerde, "output");

        final KafkaStreams payloadStream = new KafkaStreams(builder, getProperties());

        payloadStream.start();

        return false;
    }

    private Properties getProperties(){
        Properties streamsConfiguration = new Properties();

        streamsConfiguration.put(APPLICATION_ID_CONFIG, "kafkastore");
        streamsConfiguration.put(BOOTSTRAP_SERVERS_CONFIG, "kafka:9092");
        streamsConfiguration.put(DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        streamsConfiguration.put(DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());

        return streamsConfiguration;
    }
}
