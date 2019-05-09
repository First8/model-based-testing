package nl.first8.mbtdemo.persistence.jpa;

import java.util.Optional;

import org.springframework.data.repository.Repository;

@org.springframework.stereotype.Repository
public interface ArticleEntityRepository extends Repository<ArticleEntity, String> {
	ArticleEntity save(ArticleEntity article);
	Optional<ArticleEntity> findById(String title);
	void deleteById(String title);
}