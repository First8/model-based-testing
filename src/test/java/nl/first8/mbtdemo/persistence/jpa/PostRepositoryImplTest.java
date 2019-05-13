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

import nl.first8.mbtdemo.Post;

@RunWith(SpringRunner.class)
@DataJpaTest
@Import(PostRepositoryImpl.class)
public class PostRepositoryImplTest extends ExecutionContext {
	@Autowired private PostRepositoryImpl repository;

	private final Post post = new Post(//
				"Some title", //
				"some author", //
				"some content", //
				LocalDateTime.now(), //
				Collections.emptyList());
	
	public void notPresent() {
		assertFalse(repository.findByTitle(post.getTitle()).isPresent());
	}
	
	public void present() {
		assertTrue(repository.findByTitle(post.getTitle()).isPresent());
	}
	
	public void addPost() {
		repository.save(post);
	}
	
	public void updatePost() {
		repository.save(post);
	}
	
	public void removePost() {
		repository.removePost(post);
	}
	
	@Test
	public void walkCRUDArticle() throws IOException {
		// States
		final Vertex notPresent = new Vertex().setName("notPresent");
		final Vertex present = new Vertex().setName("present");
		// Transitions
		final Model model = new Model() //
				.addEdge(new Edge().setName("addPost") //
						.setSourceVertex(notPresent)//
						.setTargetVertex(present))
				.addEdge(new Edge().setName("removePost") //
						.setSourceVertex(present) //
						.setTargetVertex(notPresent)) //
				.addEdge(new Edge().setName("updatePost") //
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
