package dwaki.localkafkaconsumer.controller;

import java.util.List;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import dwaki.localkafkaconsumer.avro.model.Anime;

@Service
public class KafkaConsumerController {

	@KafkaListener(topics = "anime-review", groupId = "anime-group")
	public void listen(@Payload List<Anime> anime) {
		System.out.println("Received Messasge in group : " + anime);
	}
}
