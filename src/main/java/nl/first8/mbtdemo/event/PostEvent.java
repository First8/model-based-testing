package nl.first8.mbtdemo.event;


import lombok.NonNull;
import lombok.Value;

@Value
public class PostEvent {
    private final @NonNull String title;
    private final @NonNull String content;
    private final @NonNull String author;
    private final long publishedOn;
}
