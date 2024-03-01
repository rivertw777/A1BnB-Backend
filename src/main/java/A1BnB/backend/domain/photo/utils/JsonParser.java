package A1BnB.backend.domain.photo.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JsonParser {

    @Autowired
    private final ObjectMapper objectMapper;

    public Map<String, Map<String, Map<String, Double>>> parseInferenceResult(String inferenceResult)
            throws JsonProcessingException {
        TypeReference<Map<String, Map<String, Map<String, Double>>>> typeRef =
                new TypeReference<Map<String, Map<String, Map<String, Double>>>>() {};
        Map<String, Map<String, Map<String, Double>>> parsedData = objectMapper.readValue(inferenceResult, typeRef);
        return parsedData;
    }

}
