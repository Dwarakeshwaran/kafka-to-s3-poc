package dwaki.localkafkaproducer.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import dwaki.localkafkaproducer.avro.Serializer.AvroSerializer;
import dwaki.localkafkaproducer.avro.model.Anime;

@Configuration
public class KafkaProducerConfig {

	@Bean
	public ProducerFactory<String, Anime> getProducerFactory() {

		Map<String, Object> kafkaProperties = new HashMap<String, Object>();

		kafkaProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		kafkaProperties.put(ProducerConfig.ACKS_CONFIG, "all");
		kafkaProperties.put(ProducerConfig.RETRIES_CONFIG, 0);
		kafkaProperties.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
		kafkaProperties.put(ProducerConfig.LINGER_MS_CONFIG, 1);
		kafkaProperties.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
		kafkaProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		kafkaProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, AvroSerializer.class);

		return new DefaultKafkaProducerFactory<String, Anime>(kafkaProperties);

	}

	@Bean
	public KafkaTemplate<String, Anime> getKafkaTemplate() {

		KafkaTemplate<String, Anime> template = new KafkaTemplate<String, Anime>(getProducerFactory());

		return template;
	}

}
