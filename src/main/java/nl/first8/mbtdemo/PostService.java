package nl.first8.mbtdemo;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;

@Service
@RequiredArgsConstructor
public class PostService {
    @Delegate
    private final PostRepository repository;

    public void saveComment(Comment comment, String postTitle) {
        // TODO
        // 1 Find post, if it does not exist, create new
        // 2 Attach comment to post
        // 3 Save post, including the new comment.
    }
}
