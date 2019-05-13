package nl.first8.mbtdemo.rest;

import lombok.Value;

@Value
public class PostBody {
	private final String title;
	private final String author;
	private final String content;
}
