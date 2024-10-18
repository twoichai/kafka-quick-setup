package com.twoichai.kafka.producer.uuid;

import com.twoichai.kafka.producer.KafkaUUIDProducer;
import lombok.RequiredArgsConstructor;

import java.util.UUID;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class UUIDProducerService {

    private final KafkaUUIDProducer kafkaUUIDProducer;

    public void produceUIIDs() {
        Stream.generate(UUID::randomUUID)
                .limit(100)
                .forEach(uuid -> kafkaUUIDProducer.sendMessage(uuid.toString()));
    }
}
