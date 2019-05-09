package nl.first8.mbtdemo.persistence.jpa;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nl.first8.mbtdemo.Comment;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String author;
	private String content;
	private LocalDateTime created;
	
	Comment toComment() {
		return new Comment(id, author, content, created);
	}
	
	static CommentEntity fromComment(final Comment comment) {
		return new CommentEntity(//
				comment.getId(), //
				comment.getAuthor(), //
				comment.getContent(), //
				comment.getCreated());
	}
}
