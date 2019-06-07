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
import nl.first8.mbtdemo.Post;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostEntity {
    @Id
    private String title;
    private String content;
    private String author;
    private LocalDateTime publishedOn;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true,
            fetch = FetchType.EAGER)
    @JoinColumn(name = "post")
    @OrderBy("created ASC, id ASC")
    private List<CommentEntity> comments = new ArrayList<>();

    /*
     * Adapters to and from post
     */

    public final Post toPost() {
        return new Post(//
                title, //
                content, //
                author, //
                publishedOn, //
                comments.stream() //
                        .map(CommentEntity::toComment) //
                        .collect(Collectors.toList()));
    }

    public static PostEntity fromPost(final Post post) {
        return new PostEntity(//
                post.getTitle(), //
                post.getContent(), //
                post.getAuthor(), //
                post.getPublishedOn(), //
                post.getComments().stream() //
                        .map(CommentEntity::fromComment)
                        .collect(Collectors.toList()));
    }
}
