# kafka-store

Api
```
./gradlew build && docker-compose up --build api
```

Event
```
./gradlew build && docker-compose up --build event
```



## Kafka

### Consumer
```
docker exec kafkastore_kafka_1  /kafka/bin/kafka-console-consumer.sh --bootstrap-server kafka:9092 --topic topic_name --from-beginning
```

### Producer
```
docker exec kafkastore_kafka_1  /kafka/bin/kafka-topics.sh --zookeeper zookeeper --list
```