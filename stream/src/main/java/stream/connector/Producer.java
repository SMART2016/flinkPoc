package stream.connector;

import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.common.serialization.TypeInformationSerializationSchema;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer010;
import org.apache.flink.streaming.util.serialization.KeyedSerializationSchema;

public class Producer {

    public static FlinkKafkaProducer010<Tuple2> createStringProducer(StreamExecutionEnvironment env, String topic, String kafkaAddress) {

        return new FlinkKafkaProducer010<Tuple2>(kafkaAddress, topic, new AverageSerializer());
    }
}

class AverageSerializer implements KeyedSerializationSchema<Tuple2> {
    @Override
    public byte[] serializeKey(Tuple2 element) {
        return ("\"" + element.getField(0).toString() + "\"").getBytes();
    }

    @Override
    public byte[] serializeValue(Tuple2 element) {
        String value = "{"+ element.getField(0).toString()+": "+ element.getField(1).toString() + "}";
        return value.getBytes();
    }

    @Override
    public String getTargetTopic(Tuple2 element) {
        // use always the default topic
        return null;
    }
}
