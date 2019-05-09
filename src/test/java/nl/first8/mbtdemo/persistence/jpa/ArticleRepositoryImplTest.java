package nl.first8.mbtdemo.persistence.jpa;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.graphwalker.core.condition.EdgeCoverage;
import org.graphwalker.core.condition.TimeDuration;
import org.graphwalker.core.condition.VertexCoverage;
import org.graphwalker.core.generator.RandomPath;
import org.graphwalker.core.machine.Context;
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
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import nl.first8.mbtdemo.Article;
import nl.first8.mbtdemo.Comment;

@RunWith(SpringRunner.class)
@DataJpaTest
@Import(ArticleRepositoryImpl.class)
public class ArticleRepositoryImplTest extends ExecutionContext {
	@Autowired private ArticleRepositoryImpl repository;

	private final Article article = new Article(//
				"Some title", //
				"some author", //
				"some content", //
				LocalDateTime.now(), //
				Collections.emptyList());
	
	public void notPresent() {
		assertTrue(repository.findByName(article.getTitle()).isEmpty());
	}
	
	public void present() {
		assertTrue(repository.findByName(article.getTitle()).isPresent());
	}
	
	public void addArticle() {
		repository.save(article);
	}
	
	public void updateArticle() {
		repository.save(article);
	}
	
	public void removeArticle() {
		repository.removeArticle(article);
	}
	
	@Test
	public void walkCRUDArticle() throws IOException {
		// States
		final Vertex notPresent = new Vertex().setName("notPresent");
		final Vertex present = new Vertex().setName("present");
		// Transitions
		final Model model = new Model() //
				.addEdge(new Edge().setName("addArticle") //
						.setSourceVertex(notPresent)//
						.setTargetVertex(present))
				.addEdge(new Edge().setName("removeArticle") //
						.setSourceVertex(present) //
						.setTargetVertex(notPresent)) //
				.addEdge(new Edge().setName("updateArticle") //
						.setSourceVertex(present) //
						.setTargetVertex(present));
		
		// Build and register model
		this.setModel(model.build());
		// We aim for 100% edge coverage
		this.setPathGenerator(new RandomPath(new TimeDuration(2, TimeUnit.SECONDS)));
		// We start in the state 'notPresent'
		setNextElement(notPresent);
		
		// Build and run the machine
		final Machine machine = new SimpleMachine(this);		
		while(machine.hasNextStep()) {
			machine.getNextStep();
		}
	}
}
