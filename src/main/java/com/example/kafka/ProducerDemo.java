package com.example.kafka;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProducerDemo {

    private static final Logger log = LoggerFactory.getLogger(ProducerDemo.class.getSimpleName());

    public static void main(String[] args) {
        log.info("hello wordld");
        
        Properties properties = new Properties();

        // connect to localhost
        properties.setProperty("bootstrap.servers", "localhost:9092");

        // create Producer Properties
        properties.setProperty("key.serializer", StringSerializer.class.getName());
        properties.setProperty("value.serializer", StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.ACKS_CONFIG, "all");

        // create the Producer
        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

        // create a producer record
        ProducerRecord<String, String> producerRecord = 
                new ProducerRecord<>("demo_java", "hello world");

        // send data
        producer.send(producerRecord, (metadata, exception) -> {
            if (exception == null) {
                log.info("Mensaje enviado correctamente: topic={}, partition={}, offset={}", metadata.topic(), metadata.partition(), metadata.offset());
            } else {
                log.error("Error al enviar mensaje", exception);
            }
        });

        // tell the producer to send all data and block until done - synchronous
        producer.flush();

        // flush and close produccer
        producer.close();
    }

}
