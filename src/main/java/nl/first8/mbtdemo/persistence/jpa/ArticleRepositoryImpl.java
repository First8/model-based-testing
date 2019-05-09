package nl.first8.mbtdemo.persistence.jpa;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import lombok.RequiredArgsConstructor;
import nl.first8.mbtdemo.Article;
import nl.first8.mbtdemo.ArticleRepository;

@org.springframework.stereotype.Repository
@RequiredArgsConstructor
public class ArticleRepositoryImpl implements ArticleRepository {
	private final ArticleEntityRepository repository;

	@Override
	public Article save(final Article article) {
		return repository.save(ArticleEntity.fromArticle(article)).toArticle();
	}

	@Override
	public Optional<Article> findByName(final String name) {
		return repository.findById(name).map(ArticleEntity::toArticle);
	}

	@Override
	public void removeArticle(final Article article) {
		repository.deleteById(article.getTitle());
	}
}
