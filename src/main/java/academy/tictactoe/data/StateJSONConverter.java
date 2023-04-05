package academy.tictactoe.data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.AttributeConverter;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

public class StateJSONConverter implements AttributeConverter<char[][], String> {

    private static final Logger LOGGER = LoggerFactory.getLogger(StateJSONConverter.class);

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(char[][] primitiveMatrix) {
        String result = null;
        try {
            result = OBJECT_MAPPER.writer().writeValueAsString(primitiveMatrix);
        } catch (JsonProcessingException e) {
            LOGGER.error("Could not write to JSON the primitive matrix");
        }

        return result;
    }

    @Override
    public char[][] convertToEntityAttribute(String json) {
        char[][] result = null;
        Reader reader = new StringReader(json);
        try {
            result = OBJECT_MAPPER.readValue(reader, char[][].class);
        } catch (IOException e) {
            LOGGER.error("Could not read json to matrix of characters");
        }

        return result;
    }
}

