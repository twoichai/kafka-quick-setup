package com.twoichai.kafka.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

@Service
// @RequiredArgsConstructor
@Slf4j
public class KafkaUUIDConsumer {

    private ConcurrentLinkedQueue<String> latencies = new ConcurrentLinkedQueue<>();

    public KafkaUUIDConsumer() {
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(this::evaluateLatencies, 0, 1, TimeUnit.MINUTES);  // Run every minute
    }

    @KafkaListener(topics = "testTxtTopic", groupId = "myGroup")
    public void consume(String msg, @Header("publishingTimestamp") String publishingTimestamp) {
        String receivingTimestamp = String.valueOf(Instant.now().toEpochMilli());

        try {
            long latency = Long.parseLong(receivingTimestamp) - Long.parseLong(publishingTimestamp);

            latencies.add(String.valueOf(latency));

            log.info("Message: {} | Latency: {} ms", msg, latency);
        } catch (NumberFormatException e) {
            log.error("Error parsing timestamps: {}", e.getMessage());
        }
    }

    private void evaluateLatencies() {
        List<String> latenciesList = new ArrayList<>(latencies);

        if (!latenciesList.isEmpty()) {
            List<Long> latencyValues = new ArrayList<>();
            for (String latencyStr : latenciesList) {
                try {
                    latencyValues.add(Long.parseLong(latencyStr));
                } catch (NumberFormatException e) {
                    log.error("Error parsing latency value: {}", e.getMessage());
                }
            }

            // Calculate statistics
            if (!latencyValues.isEmpty()) {
                long sum = latencyValues.stream().mapToLong(Long::longValue).sum();
                double avgLatency = sum / (double) latencyValues.size();
                long maxLatency = latencyValues.stream().mapToLong(Long::longValue).max().orElse(0L);

                latencyValues.sort(Long::compare);
                long p50 = latencyValues.get((int) (latencyValues.size() * 0.5));
                long p90 = latencyValues.get((int) (latencyValues.size() * 0.9));
                long p99 = latencyValues.get((int) (latencyValues.size() * 0.99));

                log.info("Latency Stats for the last minute:");
                log.info("Average Latency: {} ms", avgLatency);
                log.info("Max Latency: {} ms", maxLatency);
                log.info("50th Percentile (Median) Latency: {} ms", p50);
                log.info("90th Percentile Latency: {} ms", p90);
                log.info("99th Percentile Latency: {} ms", p99);
            }

            latencies.clear();
        } else {
            log.info("No latency data to evaluate in the last minute.");
        }
    }
}