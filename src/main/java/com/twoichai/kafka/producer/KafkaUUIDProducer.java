package com.twoichai.kafka.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaUUIDProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String msg) {
        // Get the current timestamp
        long publishingTimestamp = Instant.now().toEpochMilli();
        String publishingTimestampStr = String.valueOf(publishingTimestamp);

        // Building a message including the publishing time stamp
        Message<String> message = MessageBuilder
                .withPayload(msg)
                .setHeader(KafkaHeaders.TOPIC, "testTxtTopic")
                .setHeader("publishingTimestamp", publishingTimestampStr)
                .build();
        log.info("Message with headers: {}", message.getHeaders());
        // kafkaTemplate.send(message);
        // Logging options to check if a message has been produced
        //log.info(String.format("UUID SENT: %s", message.getPayload()));

    }
}