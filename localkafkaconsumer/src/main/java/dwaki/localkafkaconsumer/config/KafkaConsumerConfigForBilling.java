package dwaki.localkafkaconsumer.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import dwaki.localkafkaconsumer.avro.deserializer.AvroDeserializer;
import dwaki.localkafkaconsumer.avro.model.BillingValue;

@Configuration
@EnableKafka
public class KafkaConsumerConfigForBilling {

	@Bean
	public ConsumerFactory<String, BillingValue> getConsumerFactoryForConfluent() {

		Map<String, Object> kafkaProperties = new HashMap<String, Object>();

		// Required connection configs for Kafka producer, consumer, and admin
		kafkaProperties.put("sasl.mechanism", "PLAIN");
		kafkaProperties.put("bootstrap.servers", "pkc-2396y.us-east-1.aws.confluent.cloud:9092");
		kafkaProperties.put("group.id", "billing");
		kafkaProperties.put("sasl.jaas.config",
				"org.apache.kafka.common.security.plain.PlainLoginModule   required username='Z2GYB57P7P44LVKL'   password='BZZbmFlu5q1CMf5qNnTUumw824ZxEpIG7qc7JMSwTJYEp+Go0PQ1ZsULStyh+k2+';");
		kafkaProperties.put("security.protocol", "SASL_SSL");
		kafkaProperties.put("auto.offset.reset", "earliest");

		// Best practice for higher availability in Apache Kafka clients prior to 3.0
		kafkaProperties.put("session.timeout.ms", 20000);
		kafkaProperties.put("max.poll.interval.ms", 20000);

		// Kafka Serializers
		kafkaProperties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		kafkaProperties.put("value.deserializer", "io.confluent.kafka.serializers.KafkaAvroDeserializer");

		// Schema Registry
		// Confluent Cloud Schema Registry
		kafkaProperties.put("basic.auth.credentials.source", "USER_INFO");
		kafkaProperties.put("basic.auth.user.info",
				"K4TJY2OV7FGEXVAS:LycVzjVJomwrsNMxH2B1SzBJwwFQrIc6qWgqX/pBY8cqlTAgcRXhVgq8LQ67Kuns");
		kafkaProperties.put("schema.registry.url", "https://psrc-o2wjx.us-east-2.aws.confluent.cloud");

		return new DefaultKafkaConsumerFactory<String, BillingValue>(kafkaProperties);
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, BillingValue> kafkaListenerContainerFactory() {

		ConcurrentKafkaListenerContainerFactory<String, BillingValue> factory = new ConcurrentKafkaListenerContainerFactory<>();
		// factory.setConsumerFactory(getConsumerFactory());
		factory.setConsumerFactory(getConsumerFactoryForConfluent());
		return factory;
	}

}
