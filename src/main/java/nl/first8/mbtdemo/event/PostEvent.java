package nl.first8.mbtdemo.event;

import java.time.LocalDateTime;

import lombok.Value;

@Value
public class PostEvent {
    private final String title;
    private final String content;
    private final String author;
    private final LocalDateTime publishedOn;
}
