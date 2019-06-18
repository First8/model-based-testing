package nl.first8.mbtdemo.event;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collections;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import nl.first8.mbtdemo.Post;
import nl.first8.mbtdemo.PostService;

@AllArgsConstructor
@Component
public class PostEventListener {

    private final PostService postService;
    
    @KafkaListener(//
            topics = TopicConfiguration.POST_TOPIC)
    public void handle(final ConsumerRecord<String, PostEvent> record) {
        final PostEvent postEvent = record.value();
        // Convert from event to domain model
        Post post = new Post(//
                postEvent.getTitle(), //
                postEvent.getContent(), //
                postEvent.getAuthor(), //
                LocalDateTime.ofEpochSecond(postEvent.getPublishedOn(), 0, ZoneOffset.UTC), //
                Collections.emptyList()
        );
        postService.save(post);
    }
}
