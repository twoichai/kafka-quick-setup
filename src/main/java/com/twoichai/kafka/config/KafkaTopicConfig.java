package com.twoichai.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    // we create a new topic in our kafka broker
    @Bean
    public NewTopic wabTopic() {
        return TopicBuilder
                .name("testTopic")
                .build();
    }
}
