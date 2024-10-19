package com.twoichai.kafka.consumer;

import com.twoichai.kafka.producer.KafkaUUIDProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaUUIDConsumer {
    private final KafkaUUIDProducer kafkaUUIDProducer;

    @KafkaListener(topics = "testTxtTopic", groupId = "myGroup")
    public void consume(String msg, @Header(KafkaHeaders.RECEIVED_TIMESTAMP) String key, @Header("publishingTimestamp") long publishingTimestamp) {
        long receivingTimestamp = Instant.now().toEpochMilli();
        // kafkaUUIDProducer.sendMessage(msg);
        // log.info(String.format("UUID CONSUMED: %s", msg));
       // System.out.println("Publishing time needed: " + (receivingTimestamp - publishingTimestamp) + " ms");
    }
}
