package nl.first8.mbtdemo.event;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.DockerComposeContainer;

import nl.first8.mbtdemo.Post;
import nl.first8.mbtdemo.PostService;

// Why the an external image? Because we need if for the system test...
// So we need to start an actual kafka during some tests.
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestPostListener {

    @MockBean
    private PostService postService;
    
    @Autowired
    private PostEventListener postEventListener;
    

    @Autowired
    private KafkaTemplate<String, PostEvent> kafkaTemplate;
    
    @ClassRule
    public static DockerComposeContainer<?> environment =
        new DockerComposeContainer<>(new File("src/main/resources/kafka/docker-compose.yml"))
                .withExposedService("kafka", 9092);
    //public static KafkaContainer kafka = new KafkaContainer();
    
    @Test
    public void something() {
        PostEvent event = new PostEvent(null, null, null, null);
        // produce message
        //kafkaTemplate.send(TopicConfiguration.POST_TOPIC, event);
        // assert postService will be called by event listener
        //Mockito.verify(postService, Mockito.atLeastOnce()).save(ArgumentMatchers.any(Post.class));
    }
}
