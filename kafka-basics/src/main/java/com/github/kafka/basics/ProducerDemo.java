package com.github.kafka.basics;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class ProducerDemo {
    public static void main(String[] args) {
        String bootstrapServers = "127.0.0.1:9092";

        // create Producer properties
        Properties properties = new Properties();
        properties.setProperty( ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers );
        properties.setProperty( ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName() );
        properties.setProperty( ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName() );

        // create the Producer
        KafkaProducer<String, String> producer = new KafkaProducer<>( properties );

        // create ProducerRecord
        ProducerRecord<String, String> producerRecord = new ProducerRecord<>("first_topic", "Hello World");

        // send data - synchronous
        producer.send( producerRecord );

        // flush data
        producer.flush();

        // flush data and close producer
        producer.close();
    }
}