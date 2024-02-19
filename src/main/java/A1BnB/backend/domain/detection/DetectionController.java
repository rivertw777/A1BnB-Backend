package A1BnB.backend.domain.detection;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/result")
public class DetectionController {

    @PostMapping("/result")
    public void sendResultToReact(@RequestBody String result) {
        // 추론 결과를 리액트로 전송하는 코드

    }

    @GetMapping("/result")
    public ResponseEntity<Void> getDetectedResult() {
        return ResponseEntity.ok().build();
    }
}