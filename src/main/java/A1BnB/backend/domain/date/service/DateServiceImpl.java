package A1BnB.backend.domain.date.service;

import A1BnB.backend.domain.date.model.entity.Date;
import A1BnB.backend.domain.date.repository.DateRepository;
import java.time.LocalDateTime;
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
    public List<Date> getDates(LocalDateTime startDate, LocalDateTime endDate) {
        List<LocalDateTime> localDateTimes = getLocalDateTimeDates(startDate, endDate);
        return saveDates(localDateTimes);
    }

    private List<Date> saveDates(List<LocalDateTime> localDateTimes) {
        return localDateTimes.stream()
                .map(this::saveDate)
                .collect(Collectors.toList());
    }

    // 시작, 종료 날짜 모두 포함한 기간
    @Override
    public List<LocalDateTime> getLocalDateTimeDates(LocalDateTime startDate, LocalDateTime endDate) {
        return Stream.iterate(startDate, date -> !date.isAfter(endDate), date -> date.plusDays(1))
                .collect(Collectors.toList());
    }

    private Date saveDate(LocalDateTime localDateTime){
        Date date = Date.builder()
                .localDateTime(localDateTime)
                .build();
        dateRepository.save(date);
        return date;
    }


    // 게시물 예약 가능 날짜 - 예약 날짜
    @Override
    @Transactional
    public List<Date> deleteFromAvailableDates(List<Date> dates, LocalDateTime checkInDate, LocalDateTime checkOutDate) {
        List<LocalDateTime> availableDates = getLocalDateTimeDates(dates);
        List<LocalDateTime> bookedDates = getLocalDateTimeDates(checkInDate, checkOutDate);
        availableDates.removeAll(bookedDates);
        return saveDates(availableDates);
    }

    // 게시물 예약 가능 날짜 + 예약 취소 날짜
    @Override
    @Transactional
    public List<Date> revertToAvailableDates(List<Date> dates, LocalDateTime checkInDate, LocalDateTime checkOutDate) {
        List<LocalDateTime> availableDates = getLocalDateTimeDates(dates);
        List<LocalDateTime> canceledDates = getLocalDateTimeDates(checkInDate, checkOutDate);
        availableDates.addAll(canceledDates);
        return saveDates(availableDates);
    }

    private List<LocalDateTime> getLocalDateTimeDates(List<Date> dates) {
        return dates.stream()
                .map(Date::getLocalDateTime)
                .collect(Collectors.toList());
    }

}
