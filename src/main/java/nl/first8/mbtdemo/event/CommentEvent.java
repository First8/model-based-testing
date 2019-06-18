package nl.first8.mbtdemo.event;

import lombok.NonNull;
import lombok.Value;

@Value
public class CommentEvent {
    private final @NonNull String postTitle;
    private final @NonNull String author;
    private final @NonNull String content;
    private final long created;
}
