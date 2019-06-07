package nl.first8.mbtdemo.event;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure the Kafka topics. Topics are like queues, except ALL listeners
 * receive all events exactly once each.
 */
@Configuration
public class TopicConfiguration {
    static final String POST_TOPIC = "posts";
    /**
     * The NewTopic return type makes KafkaAdmin create a topic.
     * 
     * @return the topic to create, if it does not exist.
     */
    @Bean
    public NewTopic createPostTopic() {
        return new NewTopic(POST_TOPIC, 1, (short) 1);
    }
}
