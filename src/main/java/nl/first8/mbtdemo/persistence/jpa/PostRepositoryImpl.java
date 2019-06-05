package nl.first8.mbtdemo.persistence.jpa;

import java.util.Optional;
import java.util.stream.Stream;

import lombok.RequiredArgsConstructor;
import nl.first8.mbtdemo.Post;
import nl.first8.mbtdemo.PostRepository;

@org.springframework.stereotype.Repository
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepository {
	private final PostEntityRepository repository;

	@Override
	public Post save(final Post post) {
		return repository.save(PostEntity.fromPost(post)).toPost();
	}

	@Override
	public Optional<Post> findByTitle(final String name) {
		return repository.findById(name).map(PostEntity::toPost);
	}

	@Override
	public void removePost(final Post post) {
		repository.deleteById(post.getTitle());
	}

	@Override
	public Stream<Post> findAll() {
		return repository.findAll().map(PostEntity::toPost);
	}

    @Override
    public Stream<Post> findAllPublished() {
        return repository.findAllPublished().map(PostEntity::toPost);
    }

    @Override
    public Optional<Post> findPublisedByTitle(String title) {
        return repository.findPublisedByTitle(title).map(PostEntity::toPost);
    }
}
