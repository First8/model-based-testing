package nl.first8.mbtdemo.persistence.jpa;

import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import nl.first8.mbtdemo.Post;

@org.springframework.stereotype.Repository
public interface PostEntityRepository extends Repository<PostEntity, String> {
	Stream<PostEntity> findAll();
	PostEntity save(PostEntity post);
	Optional<PostEntity> findById(String title);
	void deleteById(String  title);
	@Query("select e from #{#entityName} e where e.publishedOn IS NOT NULL")
    Stream<PostEntity> findAllPublished();
    @Query("select e from #{#entityName} e where e.publishedOn IS NOT NULL and e.title = ?1")
    Optional<PostEntity> findPublisedByTitle(String title);
}