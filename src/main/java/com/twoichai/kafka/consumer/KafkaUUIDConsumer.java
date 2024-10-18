package com.twoichai.kafka.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaUUIDConsumer {

    @KafkaListener(topics = "testTopic", groupId = "myGroup")
    public void consumeMsg(String msg) {
        log.info(String.format("Consuming message from test Topic: %s", msg));
    }
}
