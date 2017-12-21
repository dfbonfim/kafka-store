package kafkastore.event.topology;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KStreamBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

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
        return new Properties();
    }
}
