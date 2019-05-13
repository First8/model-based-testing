package nl.first8.mbtdemo;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Value;

@Value
public class Post {
	private final String title;
	private final String content;
	private final String author;
	private final LocalDateTime publishedOn;
	private final List<Comment> comments;
}
