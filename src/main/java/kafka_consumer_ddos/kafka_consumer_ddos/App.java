package kafka_consumer_ddos.kafka_consumer_ddos;

import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

/**
 * Kafka consumer
 *
 */
public class App 
{
	public static void main(String[] args) {
	    Properties props = new Properties();
	    props.put("bootstrap.servers", "localhost:9092");
	    props.put("group.id", "abc6");
	    props.put("enable.auto.commit", "true");
	    props.put("auto.commit.interval.ms", "1000");
	    props.put("auto.offset.reset", "earliest");
	    props.put("session.timeout.ms", "30000");
	    props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
	    props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
	    DdosDetection ddosDetection = new DdosDetection();
	    KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(props);
	    kafkaConsumer.subscribe(Arrays.asList("ddos_ph"));
	    while (true) {
	    	try{
	      ConsumerRecords<String, String> records = kafkaConsumer.poll(100);
	      for (ConsumerRecord<String, String> record : records) {
	       /* System.out.println("Partition: " + record.partition() + " Offset: " + record.offset()
	            + " Value: " + record.value() + " ThreadID: " + Thread.currentThread().getId());*/
	    	  ddosDetection.findDdosDetection(record.value());
	    	  System.out.println(record.value());
	    	  
	    	  
	      }
	    	}
	    	catch (Exception e)
	    	{
	    		System.out.println("Exception"+e.getMessage());
	    	}
	    }

	  }
}
