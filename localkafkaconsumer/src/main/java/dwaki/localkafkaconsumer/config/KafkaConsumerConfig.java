package dwaki.localkafkaconsumer.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import dwaki.localkafkaconsumer.avro.deserializer.AvroDeserializer;
import dwaki.localkafkaconsumer.avro.model.Anime;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

	@Bean
	public ConsumerFactory<String, Anime> getConsumerFactory() {

		Map<String, Object> kafkaProperties = new HashMap<String, Object>();

		kafkaProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		kafkaProperties.put(ConsumerConfig.GROUP_ID_CONFIG, "anime-group");
		kafkaProperties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringSerializer.class);
		kafkaProperties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, AvroDeserializer.class);

		return new DefaultKafkaConsumerFactory<String, Anime>(kafkaProperties, new StringDeserializer(),
				new AvroDeserializer(Anime.class));
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, Anime> kafkaListenerContainerFactory() {

		ConcurrentKafkaListenerContainerFactory<String, Anime> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(getConsumerFactory());
		return factory;
	}

}
