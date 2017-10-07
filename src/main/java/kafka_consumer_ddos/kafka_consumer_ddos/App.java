package kafka_consumer_ddos.kafka_consumer_ddos;

public class App {
	/**
	 * @function Creating an object for kafka consumer and invoking the kafka
	 *           consumer fucntion in the kafkaconsumerDdos class
	 */
	public static void main(String[] args) {

		KafkaConsumerDdos kafkaConsumerDdos = new KafkaConsumerDdos();
		kafkaConsumerDdos.KafkaConsumerFunc();
	}

}
