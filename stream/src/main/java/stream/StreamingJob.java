/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package stream;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer010;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer010;
import org.apache.flink.util.Collector;
import org.apache.kafka.clients.consumer.ConsumerConfig;

import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer010;




import static stream.connector.Consumers.createInputMessageConsumer;
import static stream.connector.Producer.createStringProducer;

/**
 * Skeleton for a Flink Streaming Job.
 *
 * <p>For a tutorial how to write a Flink streaming application, check the
 * tutorials and examples on the <a href="http://flink.apache.org/docs/stable/">Flink Website</a>.
 *
 * <p>To package your application into a JAR file for execution, run
 * 'mvn clean package' on the command line.
 *
 * <p>If you change the name of the main class (with the public static void main(String[] args))
 * method, change the respective entry in the POM.xml file (simply search for 'mainClass').
 */
public class StreamingJob {


	public static void simpleFlinkJob () throws Exception {
		String inputTopic = "EVENT_STREAM_INPUT";
		String outputTopic = "EVENT_STREAM_OUTPUT";
		String consumerGroup = "EVENT_STREAM1";
		String kafkaAddress = "kafka:9092";
		String zkAddress = "zookeeper:2181";

		StreamExecutionEnvironment environment =
				StreamExecutionEnvironment.getExecutionEnvironment();

		//environment.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);

		FlinkKafkaConsumer010<String> flinkKafkaConsumer =
				createInputMessageConsumer(inputTopic, kafkaAddress,zkAddress, consumerGroup);
		//flinkKafkaConsumer.setStartFromEarliest();

//		flinkKafkaConsumer
//				.assignTimestampsAndWatermarks(new InputMessageTimestampAssigner());
		FlinkKafkaProducer010<String> flinkKafkaProducer =
				createStringProducer(outputTopic, kafkaAddress);

		DataStream<String> inputMessagesStream =
				environment.addSource(flinkKafkaConsumer);

		inputMessagesStream.flatMap(new Tokenizer())
				// group by the tuple field "0" and sum up tuple field "1"
				.keyBy(0)
				.sum(1)
				.print();

		//inputMessagesStream.addSink(flinkKafkaProducer);

		environment.execute();
	}

	public static final class Tokenizer implements FlatMapFunction<String, Tuple2<String, Integer>> {
		@Override
		public void flatMap(String value, Collector<Tuple2<String, Integer>> out) {
			// normalize and split the line
			String[] tokens = value.toLowerCase().split("\\W+");

			// emit the pairs
			for (String token : tokens) {
				if (token.length() > 0) {
					out.collect(new Tuple2<>(token, 1));
				}
			}
		}
	}
	public static void main(String[] args) throws Exception {
		// set up the streaming execution environment
		simpleFlinkJob();
	}
}
