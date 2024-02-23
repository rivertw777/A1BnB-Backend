package A1BnB.backend.domain.photo.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.springframework.stereotype.Component;

@Component
public class JsonParser {

    /**
     * 추론 결과를 파싱하여 이미지 URL과 객체 정보를 포함하는 맵을 반환합니다.
     *
     * @param inferenceResult 추론 결과의 JSON 문자열
     * @return 이미지 URL과 객체 정보를 포함하는 맵
     * @throws JsonProcessingException JSON 파싱 중 발생한 예외
     */
    public Map<String, List<Map<String, Double>>> parseInferenceResult(String inferenceResult) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, List<Map<String, Double>>> result = new HashMap<>();

        // JSON 문자열을 JsonNode로 변환
        JsonNode jsonNode = objectMapper.readTree(inferenceResult);

        // 각 이미지에 대한 오브젝트 정보 저장
        jsonNode.fields().forEachRemaining(entry -> {
            extractObjectInfo(result, entry);
        });

        return result;
    }

    /**
     * 이미지에 대한 오브젝트 정보를 추출하여 결과 맵에 저장합니다.
     *
     * @param result 결과 맵
     * @param entry  이미지와 오브젝트 정보를 포함하는 엔트리
     */
    private static void extractObjectInfo(Map<String, List<Map<String, Double>>> result, Entry<String, JsonNode> entry) {
        String imageUrl = entry.getKey();
        JsonNode objects = entry.getValue();

        List<Map<String, Double>> objectList = new ArrayList<>();

        objects.fields().forEachRemaining(objectEntry -> {
            extractObjectEntry(objectList, objectEntry);
        });

        result.put(imageUrl, objectList);
    }

    /**
     * 오브젝트 정보를 추출하여 객체 리스트에 저장합니다.
     *
     * @param objectList   객체 리스트
     * @param objectEntry  오브젝트 정보를 포함하는 엔트리
     */
    private static void extractObjectEntry(List<Map<String, Double>> objectList, Entry<String, JsonNode> objectEntry) {
        String objectType = objectEntry.getKey();
        double objectValue = objectEntry.getValue().asDouble();

        Map<String, Double> objectMap = new HashMap<>();
        objectMap.put(objectType, objectValue);

        objectList.add(objectMap);
    }

}
