package com.twoichai.kafka.consumer;
import com.twoichai.kafka.payload.Student;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class KafkaJsonConsumerTimestamp {

    @KafkaListener(topics = "testJsonTopic", groupId = "myGroup")
    public void consume(Student student, @Header(KafkaHeaders.RECEIVED_TIMESTAMP) String key, @Header("publishingTimestamp") long publishingTimestamp) {
        long receivingTimestamp = Instant.now().toEpochMilli();
        System.out.println("Consumed message: " + student);
        System.out.println("Publishing time needed: " + (receivingTimestamp - publishingTimestamp) + " ms");
    }
}