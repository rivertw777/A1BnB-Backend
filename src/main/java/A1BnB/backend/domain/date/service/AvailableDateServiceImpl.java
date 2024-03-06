package A1BnB.backend.domain.date.service;

import A1BnB.backend.domain.date.model.entity.AvailableDate;
import A1BnB.backend.domain.date.repository.AvailableDateRepository;
import A1BnB.backend.domain.post.model.entity.Post;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AvailableDateServiceImpl implements AvailableDateService {

    private final AvailableDateRepository availableDateRepository;

    @Override
    @Transactional
    public void saveAvailableDates(Post post, List<LocalDateTime> availableDates) {
        availableDates.forEach(date -> saveAvailableDate(post, date));
    }

    private void saveAvailableDate(Post post, LocalDateTime date){
        AvailableDate availableDate = AvailableDate.builder()
                .post(post)
                .date(date)
                .build();
        System.out.println(availableDate);
        availableDateRepository.save(availableDate);
    }

    @Override
    @Transactional
    public void deleteAvailableDates(Post post, List<LocalDateTime> bookedDates) {
        bookedDates.forEach(date -> availableDateRepository.deleteByPostAndDate(post, date));
    }

}
