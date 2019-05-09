package nl.first8.mbtdemo;

import java.util.Optional;

public interface ArticleRepository {
	Optional<Article> findByName(final String name);
	Article save(Article article);
	void removeArticle(Article article);
}
