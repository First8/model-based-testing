package nl.first8.mbtdemo.rest;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.websocket.server.PathParam;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import nl.first8.mbtdemo.Post;
import nl.first8.mbtdemo.PostService;
import nl.first8.mbtdemo.Comment;

@RequiredArgsConstructor
@RestController
public class PostController {
	PostService postService;
	
	@GetMapping("/")
	public List<PostListResource> index() {
		return postService.findAll() //
				.map(PostListResource::fromPost) //
				.collect(Collectors.toList());
	}
	
	@PostMapping("/")
	public void addPost(final @RequestBody PostBody postBody) {
		final Post post = new Post(//
				postBody.getTitle(), //
				postBody.getAuthor(), //
				postBody.getContent(), //
				LocalDateTime.now(), //
				Collections.emptyList()
				);
		postService.save(post);
	}

	@GetMapping("/{title}")
	public Post getPost(final @PathParam("title") String title) {
		return postService.findByTitle(title) //
				.orElseThrow(() -> new RuntimeException("Article not found"));
	}
	
	@PostMapping("/{title}")
	public void addComment(final @PathParam("title") String title,
			final @RequestBody CommentBody commentBody) {
		final Post post = postService.findByTitle(title) //
				.orElseThrow(() -> new RuntimeException("Article not found"));
		post.getComments().add(new Comment(
				null, //
				commentBody.getAuthor(), //
				commentBody.getContent(), //
				LocalDateTime.now() //
				));
	}
}
