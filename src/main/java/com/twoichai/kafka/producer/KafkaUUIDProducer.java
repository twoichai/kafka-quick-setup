package com.twoichai.kafka.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import java.time.Instant;

@Service
@Slf4j
public class KafkaUUIDProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final AtomicInteger messageCount = new AtomicInteger(0);

    public KafkaUUIDProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(this::logThroughput, 0, 1, TimeUnit.MINUTES);  // Log every minute
    }
    public void sendMessage(String msg) {
        String publishingTimestamp = String.valueOf(Instant.now().toEpochMilli());

        Message<String> message = MessageBuilder
                .withPayload(msg)
                .setHeader(KafkaHeaders.TOPIC, "testTxtTopic")
                .setHeader("publishingTimestamp", publishingTimestamp)
                .build();

        kafkaTemplate.send(message);

        messageCount.incrementAndGet();
        //log.info("UUID SENT: {}", message.getPayload());
    }
    private void logThroughput() {
        int count = messageCount.getAndSet(0);  // Reset the counter after logging
        log.info("Producer Throughput: {} messages per minute", count);
    }
}