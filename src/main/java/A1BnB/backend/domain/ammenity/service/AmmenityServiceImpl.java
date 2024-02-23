package A1BnB.backend.domain.ammenity.service;

import A1BnB.backend.domain.ammenity.model.entity.Ammenity;
import A1BnB.backend.domain.ammenity.repository.AmmenityRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Transactional
@Service
@RequiredArgsConstructor
public class AmmenityServiceImpl implements AmmenityService {

    @Autowired
    private final AmmenityRepository ammenityRepository;

    @Override
    public Ammenity saveAmmenity(String type, Double confidence) {
        Ammenity ammenity = Ammenity.builder()
                .type(type)
                .confidence(confidence)
                .build();
        ammenityRepository.save(ammenity);
        return ammenity;
    }

}
