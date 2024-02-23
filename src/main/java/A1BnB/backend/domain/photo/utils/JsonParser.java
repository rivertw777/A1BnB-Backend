package A1BnB.backend.domain.photo.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class JsonParser {
    public Map<String, List<Map<String, Double>>> parseInferenceResult(String inferenceResult) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, List<Map<String, Double>>> result = new HashMap<>();

        // JSON 문자열을 JsonNode로 변환
        JsonNode jsonNode = objectMapper.readTree(inferenceResult);

        // 각 이미지에 대한 오브젝트 정보 저장
        jsonNode.fields().forEachRemaining(entry -> {
            String imageUrl = entry.getKey();
            JsonNode objects = entry.getValue();

            List<Map<String, Double>> objectList = new ArrayList<>();

            objects.fields().forEachRemaining(objectEntry -> {
                String objectType = objectEntry.getKey();
                double objectValue = objectEntry.getValue().asDouble();

                Map<String, Double> objectMap = new HashMap<>();
                objectMap.put(objectType, objectValue);

                objectList.add(objectMap);
            });

            result.put(imageUrl, objectList);
        });
        return result;
    }

}

