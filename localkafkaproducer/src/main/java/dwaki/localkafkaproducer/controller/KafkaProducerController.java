package dwaki.localkafkaproducer.controller;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import dwaki.localkafkaproducer.avro.model.Anime;

@Controller
@RequestMapping(value = "kafkaproducer/api")
public class KafkaProducerController {
	
	@Autowired
	private KafkaTemplate<String, Anime> kafkaTemplate;
	
	@GetMapping(value = "sendMessage")
	@ResponseBody
	public String sendMessageToKafkaTopic(@RequestBody Anime anime) {
		String topic = "anime-review";
		String key = Long.valueOf(Math.round(Math.random() * (100 - 1 + 1)) + 1).toString();
		Anime value = anime;
		
		ProducerRecord<String, Anime> record = new ProducerRecord<String, Anime>(topic, key, value);
		
		kafkaTemplate.send(record);

		return "Message Sent";
	}
}
