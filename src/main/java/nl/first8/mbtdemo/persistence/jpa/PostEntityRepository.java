package nl.first8.mbtdemo.persistence.jpa;

import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.data.repository.Repository;

@org.springframework.stereotype.Repository
public interface PostEntityRepository extends Repository<PostEntity, String> {
	Stream<PostEntity> findAll();
	PostEntity save(PostEntity post);
	Optional<PostEntity> findById(String title);
	void deleteById(String  title);
}