package nl.first8.mbtdemo.event;

import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.test.rule.EmbeddedKafkaRule;
import org.springframework.test.context.junit4.SpringRunner;

import nl.first8.mbtdemo.Post;
import nl.first8.mbtdemo.PostService;

// Why the an external image? Because we need if for the system test...
// So we need to start an actual kafka during some tests.

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestPostListener {

    @MockBean
    private PostService postService;
    
    @SuppressWarnings("unused")
    @Autowired
    private EventListener postEventListener;

    @Autowired
    private EventProducer postEventProducer;
    
    @ClassRule
    public static EmbeddedKafkaRule kafkaEmbedded = new EmbeddedKafkaRule(//
            1, true, TopicConfiguration.POST_TOPIC);

    @Test
    public void something() throws InterruptedException {
        PostEvent event = new PostEvent("", "", "", 0);
        // produce message
        postEventProducer.send(event);
        Thread.sleep(1000);
        // assert postService will be called by event listener
        Mockito //
                .verify(postService, Mockito.atLeastOnce()) //
                .save(ArgumentMatchers.any(Post.class));
        
    }
}
