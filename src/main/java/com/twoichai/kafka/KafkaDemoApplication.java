package com.twoichai.kafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class KafkaDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(KafkaDemoApplication.class, args);
	}
}
// uuid same size, check how much units per x amount of time
// todo: collect timestamp time
// todo: calculation of throughput
// through kafka, find a tool to measure latency and throughput