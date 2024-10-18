package com.twoichai.kafka.producer;

import com.twoichai.kafka.payload.Student;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class KafkaJsonProducerTimestamp {

    private final KafkaTemplate<String, Student> kafkaTemplate;

    public void sendMessage(Student student) {
        // Get the current timestamp
        long publishingTimestamp = Instant.now().toEpochMilli();

        // Build the message with a publishing timestamp in the headers
        Message<Student> message = MessageBuilder
                .withPayload(student)
                .setHeader(KafkaHeaders.TOPIC, "testJsonTopic")
                .setHeader("publishingTimestamp", publishingTimestamp)
                .build();
        kafkaTemplate.send(message);
    }
}
