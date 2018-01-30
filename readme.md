# Spark with Scala
Simple mapping and reduce operations scala 2.11.
Simple streaming from socket.
kafka streaming.

## Socket streaming
we need to create a socket and start Scala application.
```
nc -lk 9999
```

## Kafka streaming
We need to start up a kafka broker
```
docker run -d -p 2181:2181 -p 9092:9092 --env ADVERTISED_HOST=localhost --env ADVERTISED_PORT=9092 -v /DIR_TO_LOCAL_FILES:/data --name local-kafka spotify/kafka
```
Enter into kafka machine
```
docker exec -it local-kafka bash
```

Some commands to execute kafka options
```
/opt/kafka_2.11-0.10.1.0/bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 3 --topic spark
/opt/kafka_2.11-0.10.1.0/bin/kafka-console-producer.sh --broker-list localhost:9092 --topic spark
```

Kafka with messages by batches
```
/opt/kafka_2.11-0.10.1.0/bin/kafka-console-producer.sh --broker-list localhost:9092 --topic spark < FILE_WITH_MESSAGES
```