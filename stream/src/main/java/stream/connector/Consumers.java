package stream.connector;


import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer010;

import java.util.Properties;
public class Consumers {
    public static FlinkKafkaConsumer010<String> createInputMessageConsumer(String topic, String kafkaAddress,String zookeeprAddr, String kafkaGroup ) {
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", kafkaAddress);
        properties.setProperty("zookeeper.connect", zookeeprAddr);
        properties.setProperty("group.id",kafkaGroup);
        FlinkKafkaConsumer010<String> consumer = new FlinkKafkaConsumer010<String>(
                topic,new SimpleStringSchema(),properties);
        return consumer;
    }
}
