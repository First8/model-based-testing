package nl.first8.mbtdemo.event;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class EventProducer {
    private final KafkaTemplate<String, PostEvent> postKafkaTemplate;
    private final KafkaTemplate<String, CommentEvent> commentKafkaTemplate;
    
    public void send(final PostEvent postEvent) {
        postKafkaTemplate.send(TopicConfiguration.POST_TOPIC, postEvent);
    }
    
    public void send(final CommentEvent commentEvent) {
        commentKafkaTemplate.send(//
                TopicConfiguration.COMMENT_TOPIC, //
                commentEvent);
    }
}
