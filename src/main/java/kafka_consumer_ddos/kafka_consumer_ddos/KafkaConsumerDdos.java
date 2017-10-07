package kafka_consumer_ddos.kafka_consumer_ddos;

import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord; //A key/value pair to be received from Kafka. This consists of a topic name and a partition number, from which the record is being received and an offset that points to the record in a Kafka partition.
import org.apache.kafka.clients.consumer.ConsumerRecords;//A container that holds the list ConsumerRecord per partition for a particular topic. There is one ConsumerRecord list for every topic partition returned by a Consumer.poll(long) operation.
import org.apache.kafka.clients.consumer.KafkaConsumer;//A Kafka client that consumes records from a Kafka cluster.
import kafka_consumer_ddos.kafka_consumer_ddos.DdosDetection;//Importing the Ddos class created for detection

public class KafkaConsumerDdos {
	/**
	 * @function Kafka Consumer
	 */
	public void KafkaConsumerFunc() {
		Properties props = new Properties();
		props.put("bootstrap.servers", "localhost:9092");
		props.put("group.id", "test125"); // Group id of the consumer group
		props.put("enable.auto.commit", "true");
		props.put("auto.commit.interval.ms", "1000");
		props.put("auto.offset.reset", "earliest");
		props.put("session.timeout.ms", "30000");
		props.put("key.deserializer",
				"org.apache.kafka.common.serialization.StringDeserializer");
		props.put("value.deserializer",
				"org.apache.kafka.common.serialization.StringDeserializer");
		DdosDetection ddosDetection = new DdosDetection();// Object creation for
															// Ddos class
		KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(props);// Object
																					// creation
																					// for
																					// kafka
																					// consumer
																					// class
		kafkaConsumer.subscribe(Arrays.asList("ddos_ph"));// Subscribing to
															// Ddos_ph topic
		while (true) {
			try {
				ConsumerRecords<String, String> records = kafkaConsumer
						.poll(100);// Polling for messages from the topic
				for (ConsumerRecord<String, String> record : records) {
					ddosDetection.findDdosDetection(record.value());// Calling
																	// the find
																	// ddos
																	// detection
																	// function

				}
			} catch (Exception e) {
				System.out.println("Exception" + e.getMessage());
			}
		}

	}

}
