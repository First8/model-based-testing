package nl.first8.mbtdemo.rest;

import java.time.LocalDateTime;

import lombok.Value;
import nl.first8.mbtdemo.Post;

@Value
public class PostListResource {
	private final String title;
	private final String author;
	private final LocalDateTime publishedOn;
	private final int comments;
	
	public static PostListResource fromPost(Post post) {
		return new PostListResource(//
				post.getTitle(), //
				post.getAuthor(), //
				post.getPublishedOn(), //
				post.getComments().size() //
		);
	}
}
