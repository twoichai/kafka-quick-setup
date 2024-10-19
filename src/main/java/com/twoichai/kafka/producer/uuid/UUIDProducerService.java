package com.twoichai.kafka.producer.uuid;

import com.twoichai.kafka.producer.KafkaUUIDProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class UUIDProducerService {

    private final KafkaUUIDProducer kafkaUUIDProducer;

    public void produceUIIDs() {
        Stream.generate(UUID::randomUUID)
                .limit(15)
                .forEach(uuid -> kafkaUUIDProducer.sendMessage(uuid.toString()));
    }
}
