package nl.first8.mbtdemo.event;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

class PostEventTest {

    @Test
    void checkDeserialization() throws JsonParseException, JsonMappingException, IOException {
        final ObjectMapper mapper = new ObjectMapper();
        String json = "{\"title\":\"\",\"content\":\"\",\"author\":\"\",\"publishedOn\":0}";
        assertNotNull(mapper.readValue(json, PostEvent.class));
    }

}
