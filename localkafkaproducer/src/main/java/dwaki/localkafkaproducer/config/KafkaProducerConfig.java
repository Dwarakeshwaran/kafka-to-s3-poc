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


public class KafkaProducerConfig {

	@Bean
	public ProducerFactory<String, Anime> getProducerFactoryForConfluent() {
		
		

		Map<String, Object> kafkaProperties = new HashMap<String, Object>();

		// Required connection configs for Kafka producer, consumer, and admin
		kafkaProperties.put("sasl.mechanism", "PLAIN");
		kafkaProperties.put("bootstrap.servers", "pkc-2396y.us-east-1.aws.confluent.cloud:9092");
		kafkaProperties.put("sasl.jaas.config",
				"org.apache.kafka.common.security.plain.PlainLoginModule   required username='Z2GYB57P7P44LVKL'   password='BZZbmFlu5q1CMf5qNnTUumw824ZxEpIG7qc7JMSwTJYEp+Go0PQ1ZsULStyh+k2+';");
		kafkaProperties.put("security.protocol", "SASL_SSL");

		// Best practice for higher availability in Apache Kafka clients prior to 3.0
		kafkaProperties.put("session.timeout.ms", 45000);

		// Kafka Serializers
		kafkaProperties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		kafkaProperties.put("value.serializer", "io.confluent.kafka.serializers.KafkaAvroSerializer");

		// Schema Registry
		// Confluent Cloud Schema Registry
		kafkaProperties.put("basic.auth.credentials.source", "USER_INFO");
		kafkaProperties.put("basic.auth.user.info",
				"K4TJY2OV7FGEXVAS:LycVzjVJomwrsNMxH2B1SzBJwwFQrIc6qWgqX/pBY8cqlTAgcRXhVgq8LQ67Kuns");
		kafkaProperties.put("schema.registry.url", "https://psrc-o2wjx.us-east-2.aws.confluent.cloud");

		return new DefaultKafkaProducerFactory<String, Anime>(kafkaProperties);
	}

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

		// KafkaTemplate<String, Anime> template = new KafkaTemplate<String,
		// Anime>(getProducerFactory());

		KafkaTemplate<String, Anime> template = new KafkaTemplate<String, Anime>(getProducerFactoryForConfluent());

		return template;
	}

}
