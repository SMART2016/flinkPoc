# flinkPoc
Flink poc
## Project creation
mvn archetype:generate                               \
      -DarchetypeGroupId=org.apache.flink              \
      -DarchetypeArtifactId=flink-quickstart-java      \
      -DarchetypeVersion=1.2.1

## Build project:
mvn clean package -Pbuild-jar

## host resolution
> sudo vi /etc/hosts
> Add below entry in the host file to resolve kafka as localhost:
    127.0.0.1       kafka


## Produce docker message
./kafka_2.11-2.3.0/bin/kafka-console-producer.sh --broker-list localhost:9092 --topic EVENT_STREAM_INPUT

## Consumer for output topic:
./kafka_2.11-2.3.0/bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic EVENT_STREAM_OUTPUT --from-beginning


## Message:
    {
        "ID":"12C",
        "Name":"Dipanjan"
     }