package nl.first8.mbtdemo.event;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class PostEventProducer {
    private final KafkaTemplate<String, PostEvent> kafkaTemplate;
    
    public void send(final PostEvent postEvent) {
        kafkaTemplate.send(TopicConfiguration.POST_TOPIC, postEvent);
    }
}
