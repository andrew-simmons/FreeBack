package hackthemachine.ai.team_free;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import java.util.Properties;
import java.util.Arrays;

import hackthemachine.ai.team_free.json.ResponderReader;
import hackthemachine.ai.team_free.json.Responder;
import java.util.Collection;
import com.google.gson.Gson;
import java.io.InputStream;

class Main {

	private static final String RESPONDER_FILE_NAME = "responder_locations.json";
	private static final String SUPPORT_CALL_FILE_NAME = "support_calls.json";

	public static void main(String[] args) {
		// readKafkaData();
		Collection<Responder> responders = ResponderReader.read(getResourceAsStream(RESPONDER_FILE_NAME));
		Gson gson = new Gson();
		for (Responder r : responders) {
			System.out.println(gson.toJson(r));
		}
		//InputStream support_calls = getResourceAsStream(SUPPORT_CALL_FILE_NAME);
	}

	public static InputStream getResourceAsStream(String filename) {
		return Main.class.getClassLoader().getResourceAsStream(filename);
	}  

	public static void readKafkaData() {
		Properties props = new Properties();
		props.put("bootstrap.servers", "10.194.91.83:9092"); //"ip-10-194-91-83.csn.internal:9092"
		props.put("client.id", "1");
		props.put("group.id", "team3");
		props.put("enable.auto.commit", "true");
		props.put("auto.commit.interval.ms", "1000");
		props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
		consumer.subscribe(Arrays.asList("responder_locations", "support_calls"));
		while (true) {
			System.out.println(1);
			ConsumerRecords<String, String> records = consumer.poll(100);
		 	for (ConsumerRecord<String, String> record : records) {
		 		System.out.println(2);
		    	System.out.printf("offset = %d, topic = %s, key = %s, value = %s%n", 
		    		record.offset(), record.topic(), record.key(), record.value());
		 	}
		}
	}
}
