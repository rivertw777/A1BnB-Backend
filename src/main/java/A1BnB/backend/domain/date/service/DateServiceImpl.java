package A1BnB.backend.domain.date.service;

import A1BnB.backend.domain.date.model.Date;
import A1BnB.backend.domain.date.repository.DateRepository;
import A1BnB.backend.domain.postBook.model.PostBookInfo;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DateServiceImpl implements DateService {

    private final DateRepository dateRepository;

    @Override
    @Transactional
    public List<Date> getDates(LocalDateTime checkInDate, LocalDateTime checkOutDate) {
        List<LocalDateTime> localDateTimes = getLocalDateTimeDates(checkInDate, checkOutDate);
        return saveDates(localDateTimes);
    }

    // 체크인 ~ 체크아웃 날짜
    @Override
    public List<LocalDateTime> getLocalDateTimeDates(LocalDateTime checkInDate, LocalDateTime checkOutDate) {
        return Stream.iterate(checkInDate, date -> date.isBefore(checkOutDate), date -> date.plusDays(1))
                .collect(Collectors.toList());
    }

    private List<Date> saveDates(List<LocalDateTime> localDateTimes) {
        return localDateTimes.stream()
                .map(this::saveDate)
                .collect(Collectors.toList());
    }

    private Date saveDate(LocalDateTime localDateTime){
        Date date = Date.builder()
                .localDateTime(localDateTime)
                .build();
        dateRepository.save(date);
        return date;
    }

    // 가장 가까운 예약 날짜
    @Override
    public LocalDate getNearestCheckInDate(List<PostBookInfo> postBookInfos) {
        LocalDate now = LocalDate.now();
        return postBookInfos.stream()
                .map(PostBookInfo::getCheckInDate)
                .map(LocalDateTime::toLocalDate) // LocalDateTime을 LocalDate로 변환
                .filter(date -> !date.isBefore(now)) // 현재 날짜도 포함
                .min(Comparator.naturalOrder())
                .orElse(null);
    }

}
