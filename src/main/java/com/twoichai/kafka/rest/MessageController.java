package com.twoichai.kafka.rest;

import com.twoichai.kafka.payload.Student;
import com.twoichai.kafka.producer.KafkaJsonProducer;
import com.twoichai.kafka.producer.KafkaJsonProducerTimestamp;
import com.twoichai.kafka.producer.KafkaProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/messages")
@RequiredArgsConstructor
public class MessageController {

    private final KafkaProducer kafkaProducer;
    private final KafkaJsonProducer kafkaJsonProducer;
    private final KafkaJsonProducerTimestamp kafkaJsonProducerTimestamp;

    @PostMapping
    public ResponseEntity<String> sendMessage(
            @RequestBody String message
    ) {
       kafkaProducer.sendMessage(message);
       return ResponseEntity.ok("Message queued successfully");
    }

    @PostMapping("/json")
    public ResponseEntity<String> sendJsonMessage(
            @RequestBody Student message
    ) {
        kafkaJsonProducer.sendMessage(message);
       return ResponseEntity.ok("Message queued successfully as JSON");
    }

    @PostMapping("/timestamp")
    public ResponseEntity<String> sendJsonMessageTimeStamp(@RequestBody Student message) {
        // Send the message with a publishing timestamp
        kafkaJsonProducerTimestamp.sendMessage(message);
        return ResponseEntity.ok("Message queued successfully as JSON");
    }
}

