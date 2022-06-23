package dwaki.localkafkaproducer.controller;

import java.nio.ByteBuffer;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Controller;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import dwaki.localkafkaproducer.avro.model.Anime;
import dwaki.localkafkaproducer.avro.model.BillingValue;

@Controller
@RequestMapping(value = "kafkaproducer/api")
public class KafkaProducerController {

//	@Autowired
//	private KafkaTemplate<String, Anime> kafkaTemplate;

	@Autowired
	private KafkaTemplate<String, BillingValue> kafkaTemplateforBilling;

//	@GetMapping(value = "sendMessage")
//	@ResponseBody
//	public String sendMessageToKafkaTopic(@RequestBody Anime anime) {
//		String topic = "anime-review";
//		String key = Long.valueOf(Math.round(Math.random() * (100 - 1 + 1)) + 1).toString();
//		Anime value = anime;
//
//		ProducerRecord<String, Anime> record = new ProducerRecord<String, Anime>(topic, key, value);
//
//		ListenableFuture<SendResult<String, Anime>> future = kafkaTemplate.send(record);
//
//		future.addCallback(new ListenableFutureCallback<SendResult<String, Anime>>() {
//
//			@Override
//			public void onSuccess(SendResult<String, Anime> result) {
//				System.out.println(
//						"Sent message=[" + anime + "] with offset=[" + result.getRecordMetadata().offset() + "]");
//
//			}
//
//			@Override
//			public void onFailure(Throwable ex) {
//				System.out.println("Unable to send message=[" + anime + "] due to : " + ex.getMessage());
//
//			}
//		});
//
//		return "Message Sent";
//	}

	@GetMapping(value = "sendBillingValue")
	@ResponseBody
	public String sendBillingValueToKafkaTopic() {
		

		String topic = "billing-value-consumer";
		String key = Long.valueOf(Math.round(Math.random() * (100 - 1 + 1)) + 1).toString();
		BillingValue value = new BillingValue();

		value.setId(123456789012345L);
		value.setAlternateId("TEST STRING");
		value.setContractPymt(ByteBuffer.wrap("1234567890123.12".getBytes()));
		value.setGrossContract(ByteBuffer.wrap("1234567890123.12".getBytes()));
		value.setInvoiceCode("TEST STRING");
		value.setPymtsReceived(ByteBuffer.wrap("1234567890123.12".getBytes()));
		value.setPymtsReceivedD(ByteBuffer.wrap("1234567890123.12".getBytes()));
		value.setVariablePymtCode(12345);

		ProducerRecord<String, BillingValue> record = new ProducerRecord<String, BillingValue>(topic, key, value);

		ListenableFuture<SendResult<String, BillingValue>> future = kafkaTemplateforBilling.send(record);

		future.addCallback(new ListenableFutureCallback<SendResult<String, BillingValue>>() {

			@Override
			public void onSuccess(SendResult<String, BillingValue> result) {
				System.out.println(
						"Sent message=[" + value + "] with offset=[" + result.getRecordMetadata().offset() + "]");

			}

			@Override
			public void onFailure(Throwable ex) {
				System.out.println("Unable to send message=[" + value + "] due to : " + ex.getMessage());

			}
		});

		return "Message Sent";
	}

}
