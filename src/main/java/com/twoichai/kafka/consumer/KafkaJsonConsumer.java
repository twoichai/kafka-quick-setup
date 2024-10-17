package com.twoichai.kafka.consumer;

import com.twoichai.kafka.payload.Student;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaJsonConsumer {

    @KafkaListener(topics = "testTopic", groupId = "myGroup")
    public void consumeJsonMsg(Student student){
        log.info(String.format("Consuming message from test Topic: %s", student));
    }
}
