package kafkastore.event.resource.serializer;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class JsonPOJODeserializer<T> implements Deserializer<T> {
    private ObjectMapper objectMapper = new ObjectMapper();

    private Class<T> debziumClass;
    private Class resourceClass;

    /**
     * Default constructor needed by Kafka
     */
    public JsonPOJODeserializer() {
    }

    public JsonPOJODeserializer(Class<T> debziumClass, Class resourceClass) {
        this.debziumClass = debziumClass;
        this.resourceClass = resourceClass;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void configure(Map<String, ?> props, boolean isKey) {

    }

    @Override
    public T deserialize(String topic, byte[] bytes) {
        if (bytes == null)
            return null;

        T data;
        try {
            JavaType type = objectMapper.getTypeFactory().constructParametricType(debziumClass, resourceClass);
            data =  objectMapper.readValue(bytes, type);
        } catch (Exception e) {
            throw new SerializationException(e);
        }

        return data;
    }

    @Override
    public void close() {

    }
}
