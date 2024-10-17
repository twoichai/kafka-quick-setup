package com.twoichai.kafka.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    // que a message to kafka server
    public void sendMessage(String msg) {
        log.info(String.format("Sending message to test Topic: %s", msg));
        kafkaTemplate.send("testTopic", msg);
    }
}
