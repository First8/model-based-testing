package nl.first8.mbtdemo.rest;

import lombok.Value;

@Value
public class CommentBody {
	private final String author;
	private final String content;
}
