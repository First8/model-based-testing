package nl.first8.mbtdemo.event;

import java.time.LocalDateTime;
import java.util.Collections;

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
            topics = TopicConfiguration.POST_TOPIC, //
            groupId = "mbt")
    public void handle(final PostEvent postEvent) {
        // Convert from event to domain model
        Post post = new Post(//
                postEvent.getTitle().toString(), //
                postEvent.getContent().toString(), //
                postEvent.getAuthor().toString(), //
                LocalDateTime.from(postEvent.getPublishedOn()), //
                Collections.emptyList()
        );
        postService.save(post);
    }
}
