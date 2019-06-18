package nl.first8.mbtdemo;

import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.rule.EmbeddedKafkaRule;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

    /**
     * Context only loads if the placeholder ${spring.embedded.kafka.brokers}
     * evaluates to something.
     */
    @ClassRule
    public static EmbeddedKafkaRule kafkaEmbedded = new EmbeddedKafkaRule(//
            1, true, "unfortunate");

    @Test
    public void contextLoads() {
    }

}
