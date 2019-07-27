package stream.connector;

import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer010;

public class Producer {

    public static FlinkKafkaProducer010<String> createStringProducer(String topic, String kafkaAddress) {
        return new FlinkKafkaProducer010<>(kafkaAddress, topic, new SimpleStringSchema());
    }
}
