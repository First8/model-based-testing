package nl.first8.mbtdemo.event;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collections;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import nl.first8.mbtdemo.Comment;
import nl.first8.mbtdemo.Post;
import nl.first8.mbtdemo.PostService;

@AllArgsConstructor
@Component
public class EventListener {

    private final PostService postService;
    
    @KafkaListener(//
            topics = TopicConfiguration.POST_TOPIC)
    public void handlePost(final ConsumerRecord<String, PostEvent> record) {
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
    
    @KafkaListener(//
            topics = TopicConfiguration.COMMENT_TOPIC)
    public void handleComment(final ConsumerRecord<String, CommentEvent> record) {
        final CommentEvent postEvent = record.value();
        // Convert from event to domain model
        Comment post = new Comment(//
                postEvent.getContent(), //
                postEvent.getAuthor(), //
                LocalDateTime.ofEpochSecond(postEvent.getCreated(), 0, ZoneOffset.UTC)
        );
        postService.saveComment(post, postEvent.getPostTitle());
    }
}
