package infosys.poc.kafkatos3.service;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class MyKafkaConsumer {

    public MyKafkaConsumer() {
        Properties properties = new Properties();



        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "b-2.practice-cluster.blhdpn.c25.kafka.us-east-1.amazonaws.com:9094,b-3.practice-cluster.blhdpn.c25.kafka.us-east-1.amazonaws.com:9094,b-1.practice-cluster.blhdpn.c25.kafka.us-east-1.amazonaws.com:9094");
        //properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "group_id");
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"earliest");
        System.out.println("Check");
        KafkaConsumer consumer = new KafkaConsumer(properties);
        consumer.subscribe(Arrays.asList("messages"));


        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String, String> record : records) {
                System.out.println("Key: " + record.key() + ", Value:" + record.value());
                System.out.println("Partition:" + record.partition() + ",Offset:" + record.offset());
            }
        }

    }

}
