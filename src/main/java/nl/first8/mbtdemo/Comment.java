package nl.first8.mbtdemo;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import lombok.Value;

@Value
public class Comment {
	private final Long id;
	private final @NotNull String author;
	private final @NotNull String content;
	private final @NotNull LocalDateTime created;
}
