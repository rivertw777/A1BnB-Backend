package A1BnB.backend.domain.date.service;

import A1BnB.backend.domain.date.model.entity.Date;
import A1BnB.backend.domain.postBook.model.PostBookInfo;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface DateService {
    List<Date> getDates(LocalDateTime startDate, LocalDateTime endDate);
    List<LocalDateTime> getLocalDateTimeDates(LocalDateTime startDate, LocalDateTime endDate);
    List<Date> deleteFromAvailableDates(List<Date> dates, LocalDateTime checkInDate, LocalDateTime checkOutDate);
    List<Date> revertToAvailableDates(List<Date> dates, LocalDateTime checkInDate, LocalDateTime checkOutDate);
    LocalDateTime getNearestCheckInDate(List<PostBookInfo> postBookInfos);
}
