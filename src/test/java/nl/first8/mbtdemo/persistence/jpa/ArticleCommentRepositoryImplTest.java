package nl.first8.mbtdemo.persistence.jpa;

import static org.junit.Assert.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import org.graphwalker.core.condition.EdgeCoverage;
import org.graphwalker.core.condition.TimeDuration;
import org.graphwalker.core.generator.RandomPath;
import org.graphwalker.core.machine.ExecutionContext;
import org.graphwalker.core.machine.Machine;
import org.graphwalker.core.machine.SimpleMachine;
import org.graphwalker.core.model.Edge;
import org.graphwalker.core.model.Model;
import org.graphwalker.core.model.Vertex;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import nl.first8.mbtdemo.Article;
import nl.first8.mbtdemo.Comment;

@RunWith(SpringRunner.class)
@DataJpaTest
@Import(ArticleRepositoryImpl.class)
public class ArticleCommentRepositoryImplTest extends ExecutionContext {
	@Autowired
	private ArticleRepositoryImpl repository;

	private final String title = "Some title";

	private final Article article = new Article(//
			title, //
			"some author", //
			"some content", //
			LocalDateTime.now(), //
			Collections.emptyList());

	private final Comment comment = new Comment(//
			null, "some author", //
			"some content", //
			LocalDateTime.now());

	// States
	public void notPresent() {
		System.out.println("notPresent");
		assertTrue(repository.findByName(title).isEmpty());
	}

	public void present() {
		System.out.println("present");
		assertTrue(repository.findByName(title).isPresent());
		assertEquals(0, //
				repository.findByName(title).get().getComments().size());
	}

	public void hasComment() {
		System.out.println("hasComment");
		assertTrue(repository.findByName(title).isPresent());
		assertEquals(1, //
				repository.findByName(title).get().getComments().size());
	}

	// Transitions
	public void addArticle() {
		repository.save(article);
	}

	public void removeArticle() {
		repository.removeArticle(article);
	}

	public void addComment() {
		Article model = repository.findByName(article.getTitle()).get();
		model.getComments().add(comment);
		repository.save(model);
	}

	public void removeComment() {
		repository.save(article);
	}

	@Test
	public void walkCRUDArticleAndComment() throws IOException {
		// States
		final Vertex notPresent = new Vertex().setName("notPresent");
		final Vertex present = new Vertex().setName("present");
		final Vertex hasComment = new Vertex().setName("hasComment");
		// Transitions
		final Model model = new Model() //
				.addEdge(new Edge().setName("addArticle") //
						.setSourceVertex(notPresent)//
						.setTargetVertex(present))
				.addEdge(new Edge().setName("removeArticle") //
						.setSourceVertex(present) //
						.setTargetVertex(notPresent)) //
				.addEdge(new Edge().setName("addComment") //
						.setSourceVertex(present) //
						.setTargetVertex(hasComment)) //
				.addEdge(new Edge().setName("removeComment") //
						.setSourceVertex(hasComment) //
						.setTargetVertex(present));

		// Build and register model
		this.setModel(model.build());
		// We aim for 100% edge coverage
		this.setPathGenerator(new RandomPath(new TimeDuration(2, TimeUnit.SECONDS)));
		// We start in the state 'notPresent'
		setNextElement(notPresent);

		// Build and run the machine
		final Machine machine = new SimpleMachine(this);
		while (machine.hasNextStep()) {
			machine.getNextStep();
		}
	}
}
