package com.twoichai.kafka.producer.uuid;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;

@RequiredArgsConstructor
public class UUIDProducerRunner implements CommandLineRunner {

    private final UUIDProducerService uuidProducerService;

    @Override
    public void run(String... args) throws Exception {
        uuidProducerService.produceUIIDs();
    }
}
