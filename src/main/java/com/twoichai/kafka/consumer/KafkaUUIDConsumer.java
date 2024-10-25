package com.twoichai.kafka.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Slf4j
public class KafkaUUIDConsumer {

    private final AtomicInteger messageCount = new AtomicInteger(0);

    private final ConcurrentLinkedQueue<String> latencies = new ConcurrentLinkedQueue<>();

    public KafkaUUIDConsumer() {
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(this::logThroughputAndEvaluateLatencies, 0, 1, TimeUnit.MINUTES);  // Log every minute
    }

    @KafkaListener(topics = "testTxtTopic", groupId = "myGroup")
    public void consume(String msg, @Header("publishingTimestamp") String publishingTimestamp) {
        String receivingTimestamp = String.valueOf(Instant.now().toEpochMilli());
        try {
            long latency = Long.parseLong(receivingTimestamp) - Long.parseLong(publishingTimestamp);
            latencies.add(String.valueOf(latency));
            //log.info("Message: {} | Latency: {} ms", msg, latency);
        } catch (NumberFormatException e) {
            log.error("Error parsing timestamp: {}", e.getMessage());
        }
        messageCount.incrementAndGet();
    }

    private void logThroughputAndEvaluateLatencies() {
        int count = messageCount.getAndSet(0);  // Reset the counter after logging
        log.info("Consumer Throughput: {} messages per minute", count);

        if (!latencies.isEmpty()) {
            List<String> latencyList = new ArrayList<>(latencies);
            latencies.clear();

            List<Long> latencyValues = new ArrayList<>();
            for (String latencyStr : latencyList) {
                try {
                    latencyValues.add(Long.parseLong(latencyStr));
                } catch (NumberFormatException e) {
                    log.error("Error parsing latency: {}", e.getMessage());
                }
            }

            if (!latencyValues.isEmpty()) {
                long sum = latencyValues.stream().mapToLong(Long::longValue).sum();
                double avgLatency = sum / (double) latencyValues.size();
                long maxLatency = latencyValues.stream().mapToLong(Long::longValue).max().orElse(0L);

                latencyValues.sort(Long::compare);
                long p50 = latencyValues.get((int) (latencyValues.size() * 0.5));
                long p90 = latencyValues.get((int) (latencyValues.size() * 0.9));
                long p99 = latencyValues.get((int) (latencyValues.size() * 0.99));
                log.info("Average Latency: {} ms", avgLatency);
                log.info("Max Latency: {} ms", maxLatency);
                log.info("50th Percentile (Median) Latency: {} ms", p50);
                log.info("90th Percentile Latency: {} ms", p90);
                log.info("99th Percentile Latency: {} ms", p99);
            }
        } else {
            log.info("No latency data to evaluate in the last minute.");
        }
    }
}