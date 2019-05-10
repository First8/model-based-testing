package nl.first8.mbtdemo.persistence.jpa;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nl.first8.mbtdemo.Article;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ArticleEntity {
	@Id
	private String title;
	private String content;
	private String author;
	private LocalDateTime publishedOn;
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	@JoinColumn(name = "article")
	@OrderBy // orders by id, the primary key of comment
	private List<CommentEntity> comments = new ArrayList<>();
	
	/*
	 * Adapters to and from article
	 */
	
	public final Article toArticle() {
		return new Article(//
				title, //
				content, //
				author, //
				publishedOn, //
				comments.stream() //
						.map(CommentEntity::toComment) //
						.collect(Collectors.toList()));
	}
	
	public static ArticleEntity fromArticle(final Article article) {
		return new ArticleEntity(//
				article.getTitle(), //
				article.getContent(), //
				article.getAuthor(), //
				article.getPublishedOn(), //
				article.getComments().stream() //
						.map(CommentEntity::fromComment)
						.collect(Collectors.toList())
				);
	}
}
