package nl.first8.mbtdemo;

import java.util.Optional;
import java.util.stream.Stream;

public interface PostRepository {
    Stream<Post> findAll();

    Stream<Post> findAllPublished();

    Optional<Post> findByTitle(String title);

    Optional<Post> findPublisedByTitle(String title);

    Post save(Post post);

    void removePost(Post post);
}
