package A1BnB.backend.domain.date.service;

import A1BnB.backend.domain.date.model.Date;
import A1BnB.backend.domain.postBook.model.PostBookInfo;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface DateService {
    List<Date> getDates(LocalDateTime checkInDate, LocalDateTime checkOutDate);
    List<LocalDateTime> getLocalDateTimeDates(LocalDateTime checkInDate, LocalDateTime checkOutDate);
    LocalDate getNearestCheckInDate(List<PostBookInfo> postBookInfos);
}
