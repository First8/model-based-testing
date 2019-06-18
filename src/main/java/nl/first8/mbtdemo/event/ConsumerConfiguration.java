package nl.first8.mbtdemo.event;

import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;

/**
 * Configuration to enable Kafka. All other configuration of kafka is done in 
 * application.json.
 */
@EnableKafka
@Configuration
public class ConsumerConfiguration {
}
