package com.github.kafka.basics;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class ProducerDemoWithCallback {
    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(ProducerDemoWithCallback.class.getName());

        String bootstrapServers = "127.0.0.1:9092";

        // create Producer properties
        Properties properties = new Properties();
        properties.setProperty( ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers );
        properties.setProperty( ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName() );
        properties.setProperty( ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName() );

        // create the Producer
        KafkaProducer<String, String> producer = new KafkaProducer<>( properties );

        for ( int messageNumber = 0; messageNumber <= 10; messageNumber ++ ) {

            String topic = "first_topic";
            String value = "Hello World" + messageNumber;

            // create ProducerRecord
            ProducerRecord<String, String> producerRecord = new ProducerRecord<>(topic, value);

            // send data - synchronous with callback
            producer.send(producerRecord, new Callback() {
                @Override
                public void onCompletion(RecordMetadata metadata, Exception exception) {
                    // execute every time a record is successfully sent or an exception is throw
                    if (exception == null) {
                        // the record was successfully sent
                        logger.info("Received new metadata. \n " +
                                "Topic: " + metadata.topic() + "\n" +
                                "Partition: " + metadata.partition() + "\n" +
                                "Offset: " + metadata.offset() + "\n" +
                                "Timestamp: " + metadata.timestamp());
                    } else {
                        logger.error("Error while producing", exception);
                    }
                }
            });
        }

        // flush data
        producer.flush();

        // flush data and close producer
        producer.close();
    }
}