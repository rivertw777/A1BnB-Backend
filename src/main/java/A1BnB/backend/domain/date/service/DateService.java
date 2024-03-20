package A1BnB.backend.domain.date.service;

import A1BnB.backend.domain.date.model.entity.Date;
import A1BnB.backend.domain.postBook.model.PostBookInfo;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface DateService {
    List<Date> getDates(LocalDateTime checkInDate, LocalDateTime checkOutDate);
    List<LocalDateTime> getLocalDateTimeDates(LocalDateTime checkInDate, LocalDateTime checkOutDate);
    LocalDate getNearestCheckInDate(List<PostBookInfo> postBookInfos);
}
