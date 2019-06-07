package nl.first8.mbtdemo.event;

import org.springframework.boot.autoconfigure.kafka.ConcurrentKafkaListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.converter.RecordMessageConverter;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;

/**
 * Configure kafka listeners.
 */
@EnableKafka
@Configuration
public class ConsumerConfiguration {
 
    @Bean
    public ConcurrentKafkaListenerContainerFactory<?, ?> //
    kafkaListenerContainerFactory(//
            final ConcurrentKafkaListenerContainerFactoryConfigurer configurer,
            final ConsumerFactory<Object, Object> kafkaConsumerFactory, //
            final KafkaTemplate<Object, Object> template) {
        final ConcurrentKafkaListenerContainerFactory<Object, Object> factory;
        factory = new ConcurrentKafkaListenerContainerFactory<>();
        configurer.configure(factory, kafkaConsumerFactory);
        return factory;
    }
    
    /**
     * @return message converter to convert from JSON.
     */
    @Bean
    public RecordMessageConverter converter() {
        return new StringJsonMessageConverter();
    }
}
